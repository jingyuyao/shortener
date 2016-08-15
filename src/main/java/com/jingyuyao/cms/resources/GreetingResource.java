package com.jingyuyao.cms.resources;

import com.jingyuyao.cms.core.Greeting;
import com.jingyuyao.cms.db.GreetingDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/greeting")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {
    private final GreetingDAO dao;

    @Inject
    GreetingResource(GreetingDAO dao) {
        this.dao = dao;
    }

    @GET
    @UnitOfWork
    public Response getGreetings() {
        return Response.ok(dao.findAll()).build();
    }

    @GET
    @Path("/{text}")
    @UnitOfWork
    public Response getGreeting(@PathParam("text") String text) {
        try {
            Greeting greeting = dao.getOrCreate(text);
            greeting.setVisits(greeting.getVisits() + 1);
            return Response.ok(dao.save(greeting)).build();
        } catch (HibernateException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
