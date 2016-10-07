package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.api.ApiError;
import com.jingyuyao.shortener.api.CreateLink;
import com.jingyuyao.shortener.api.ShortenedLink;
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

    @Inject
    LinkResource(Validator validator, LinkDAO dao, Jedis jedis) {
        this.validator = validator;
        this.dao = dao;
        this.jedis = jedis;
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
    @UnitOfWork
    @Path("/{id}")
    public Response redirect(@PathParam("id") String id) {
        if (jedis.exists(id)) {
            Optional<URI> optionalUri = getUri(jedis.get(id));
            if (!optionalUri.isPresent()) {
                jedis.del(id);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

            // TODO: This doesn't work since hibernate session is only alive during the request
            // We need to create a new class that is not bounded to the request using the following method
            // http://www.dropwizard.io/1.0.0/docs/manual/hibernate.html#transactional-resource-methods
            // then we can invoke the saving using that object instead. This means we can probably move
            // @UnitOfWork to that class entirely
            new Thread(() -> {
                Optional<Link> optionalLink = getLink(id);
                if (optionalLink.isPresent()) {
                    saveAnalytics(optionalLink.get());
                }
            }).start();

            return Response.temporaryRedirect(optionalUri.get()).build();
        }

        Optional<Link> optionalLink = getLink(id);
        if (!optionalLink.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Optional<URI> optionalUri = getUri(optionalLink.get().getUrl());
        if (!optionalUri.isPresent()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        jedis.set(id, optionalLink.get().getUrl());
        saveAnalytics(optionalLink.get());

        return Response.temporaryRedirect(optionalUri.get()).build();
    }

    private Optional<Link> getLink(String id) {
        return dao.getById(IdEncoder.decode(id));
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

    private void saveAnalytics(Link link) {
        link.setVisits(link.getVisits() + 1);
        dao.save(link);
    }
}
