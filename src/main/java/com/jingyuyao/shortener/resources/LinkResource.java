package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.api.ApiError;
import com.jingyuyao.shortener.api.CreateLink;
import com.jingyuyao.shortener.api.ShortenedLink;
import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.core.IdEncoder;
import com.jingyuyao.shortener.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;

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

    @Inject
    LinkResource(Validator validator, LinkDAO dao) {
        this.validator = validator;
        this.dao = dao;
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
        int decodedId = IdEncoder.decode(id);
        Optional<Link> optionalLink = dao.getById(decodedId);

        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            URI redirectUri;

            try {
                redirectUri = URI.create(link.getUrl());
            } catch (IllegalArgumentException e) {
                // Catches malformed URL. Return internal error since this shouldn't be possible.
                // TODO: Should we delete the link in this case?
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

            link.setVisits(link.getVisits() + 1);
            dao.save(link);

            return Response.temporaryRedirect(redirectUri).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
