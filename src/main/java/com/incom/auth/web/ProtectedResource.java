package com.incom.auth.web;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/app")
@DenyAll
public class ProtectedResource {


    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance dashboard(String username, String role);
    }

    @GET
    @Path("/dashboard")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("admin")
    public TemplateInstance dashboard(@Context SecurityIdentity identity) {
        String username = identity.getPrincipal().getName();
        String role = identity.getRoles().stream().findFirst().orElse("user");
        return Templates.dashboard(username, role);
    }
}
