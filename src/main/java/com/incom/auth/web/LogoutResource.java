package com.incom.auth.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/logout")
public class LogoutResource {

    @GET
    public Response logout() {
        NewCookie removeCookie = new NewCookie.Builder("quarkus-credential")
                .path("/")
                .maxAge(0)
                .build();
        return Response.seeOther(URI.create("/login"))
                .cookie(removeCookie)
                .build();
    }
}
