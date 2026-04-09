package com.incom.auth.web;

import com.incom.auth.persistence.model.User;
import com.incom.auth.service.UserService;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/app/users")
@DenyAll
public class UserResource {

    private final UserService userService;
    private final Template showUsersTemplate;
    private final Template modifyUserTemplate;

    public UserResource(
            UserService userService,
            @Location("users.qute.html") Template showUsersTemplate,
            @Location("users.modify.qute.html") Template modifyUserTemplate
    ) {
        this.userService = userService;
        this.showUsersTemplate = showUsersTemplate;
        this.modifyUserTemplate = modifyUserTemplate;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("admin")
    public TemplateInstance showUsers() {
        List<User> users = userService.findAll();
        return showUsersTemplate.data("users", users);
    }

    @GET
    @Path("/modify/{username}")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("admin")
    public TemplateInstance showModifyUserForm(
            @PathParam("username") String username
    ) {
        User user = userService.getByUsername(username);
        return modifyUserTemplate.data("user", user)
                .data("errorMessage", null);
    }

    @POST
    @Path("/modify/{username}")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("admin")
    public Response modifyUser(
            @PathParam("username") String username,
            @FormParam("username") String usernameFromForm,
            @FormParam("role") String roleFromForm
    ) {
        Optional<String> errore = userService.modifyUser(username, usernameFromForm, roleFromForm);
        if (errore.isPresent()) {
            return Response.ok(
                    modifyUserTemplate
                            .data("user", userService.getByUsername(username))
                            .data("errorMessage", errore.get())
            ).build();
        } else {
            return Response.seeOther(URI.create("/app/users")).build();
        }
    }
}
