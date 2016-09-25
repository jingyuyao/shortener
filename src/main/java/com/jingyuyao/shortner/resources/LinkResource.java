package com.jingyuyao.shortner.resources;

import com.jingyuyao.shortner.api.CreateLink;
import com.jingyuyao.shortner.api.Error;
import com.jingyuyao.shortner.core.Link;
import com.jingyuyao.shortner.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

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
        return Response.ok(dao.findAll()).build();
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
            return Response.ok(dao.save(newLink)).build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Error.create(violations))
                    .build();
        }
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Response getLink(@PathParam("id") long id) {
        Optional<Link> optionalLink = dao.getById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            URI redirectLink;

            try {
                redirectLink = URI.create(link.getUrl());
            } catch (IllegalArgumentException e) {
                // Catches malformed URL. Return internal error since this shouldn't be possible.
                // TODO: Should we delete the link in this case?
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

            link.setVisits(link.getVisits() + 1);
            dao.save(link);

            return Response.temporaryRedirect(redirectLink).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
