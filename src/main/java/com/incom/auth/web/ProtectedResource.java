package com.incom.auth.web;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/app")
public class ProtectedResource {

    private final SecurityIdentity identity;

    public ProtectedResource(SecurityIdentity identity) {
        this.identity = identity;
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance dashboard(String username, String role);
    }

    @GET
    @Path("/dashboard")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dashboard() {
        String username = identity.getPrincipal().getName();
        String role = identity.getRoles().stream().findFirst().orElse("user");
        return Templates.dashboard(username, role);
    }
}
