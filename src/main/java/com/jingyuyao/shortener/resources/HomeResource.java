package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.views.HomeView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {
    @GET
    public HomeView getHome() {
        return new HomeView();
    }
}
