package com.jingyuyao.cms.resources;

import com.jingyuyao.cms.core.GreetingService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/api/greeting")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {
    private static final Logger logger = Logger.getLogger("GreetingResource");

    private final GreetingService service;
    private Request request;

    @Inject
    public GreetingResource(GreetingService service) {
        this.service = service;
    }

    @GET
    public Response getGreeting() {
        logger.info(request.toString());
        return Response.ok(service.getGreeting()).build();
    }

    @Context
    public void setRequest(Request request) {
        this.request = request;
    }
}
