package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.api.CreateLink;
import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LinkResource {
    private final LinkDAO dao;

    @Inject
    LinkResource(LinkDAO dao) {
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
        try {
            Link newLink = dao.createLink(createLink.getUrl());
            return Response.ok(dao.save(newLink)).build();
        } catch (HibernateException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
