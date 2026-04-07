package com.incom.auth.web;

import com.incom.auth.service.UserService;
import com.incom.auth.service.UserService.RegistrationResult;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/register")
@DenyAll
public class RegistrationResource {

    private final UserService userService;

    public RegistrationResource(UserService userService) {
        this.userService = userService;
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance register(String errorMessage);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public TemplateInstance showForm() {
        return Templates.register(null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public Response register(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("confirmPassword") String confirmPassword) {

        RegistrationResult result = userService.register(username, password, confirmPassword);

        if (!result.ok()) {
            return Response.ok(Templates.register(result.errorMessage())).build();
        }
        return Response.seeOther(URI.create("/login")).build();
    }
}
