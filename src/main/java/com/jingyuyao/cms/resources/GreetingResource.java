package com.jingyuyao.cms.resources;

import com.jingyuyao.cms.core.GreetingService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/greeting")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {
    private final GreetingService service;

    @Inject
    GreetingResource(GreetingService service) {
        this.service = service;
    }

    @GET
    public Response getGreeting() {
        return Response.ok(service.getGreeting()).build();
    }
}
