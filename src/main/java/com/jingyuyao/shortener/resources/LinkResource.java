package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.api.ApiError;
import com.jingyuyao.shortener.api.CreateLink;
import com.jingyuyao.shortener.api.ShortenedLink;
import com.jingyuyao.shortener.core.AnalyticsProcessor;
import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.core.IdEncoder;
import com.jingyuyao.shortener.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LinkResource {
    private final Validator validator;
    private final LinkDAO dao;
    private final Jedis jedis;
    private final AnalyticsProcessor analyticsProcessor;

    @Inject
    LinkResource(Validator validator, LinkDAO dao, Jedis jedis, AnalyticsProcessor analyticsProcessor) {
        this.validator = validator;
        this.dao = dao;
        this.jedis = jedis;
        this.analyticsProcessor = analyticsProcessor;
    }

    @GET
    @UnitOfWork
    public Response getLinks() {
        List<ShortenedLink> shortenedLinks =
                dao.findAll().stream().map(ShortenedLink::new).collect(Collectors.toList());

        return Response.ok(shortenedLinks).build();
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLink(CreateLink createLink) {
        Link newLink = new Link();
        newLink.setUrl(createLink.getUrl());
        newLink.setVisits(0);

        Set<ConstraintViolation<Link>> violations = validator.validate(newLink);

        if (violations.isEmpty()) {
            Link savedNewLink = dao.save(newLink);
            ShortenedLink shortenedLink = new ShortenedLink(savedNewLink);

            return Response.ok(shortenedLink).build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.create(violations))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response redirect(@PathParam("id") String id) {
        // No need to use @UnitOfWork annotation since we cheated by using analyticsProcessor to
        // find the link for us. This avoids getting an unused JDBC connection when we have a cache hit
        if (jedis.exists(id)) {
            return redirectCached(id);
        }
        return redirectNotCached(id);
    }

    private Response redirectNotCached(String id) {
        Optional<Link> optionalLink = analyticsProcessor.visited(IdEncoder.decode(id));
        if (!optionalLink.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Optional<URI> optionalUri = getUri(optionalLink.get().getUrl());
        if (!optionalUri.isPresent()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        jedis.set(id, optionalLink.get().getUrl());

        return Response.temporaryRedirect(optionalUri.get()).build();
    }

    private Response redirectCached(String id) {
        Optional<URI> optionalUri = getUri(jedis.get(id));
        if (!optionalUri.isPresent()) {
            jedis.del(id);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        new Thread(() -> analyticsProcessor.visited(IdEncoder.decode(id))).start();

        return Response.temporaryRedirect(optionalUri.get()).build();
    }

    private Optional<URI> getUri(String url) {
        try {
            URI uri = URI.create(url);
            return Optional.of(uri);
        } catch (IllegalArgumentException e) {
            // Catches malformed URL.
            return Optional.empty();
        }
    }
}
