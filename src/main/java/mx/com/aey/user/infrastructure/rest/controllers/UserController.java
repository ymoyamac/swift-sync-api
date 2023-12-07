package mx.com.aey.user.infrastructure.rest.controllers;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.user.infrastructure.rest.dto.UserDto;
import mx.com.aey.util.error.ErrorCode;

import java.util.UUID;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;

    @GET()
    @Path("/user/{userId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") UUID userId) {
        return this.userService.getUserById(userId)
                .map(user -> Response.ok(UserDto.fromEntity(user)))
                .getOrElseGet(res -> Response.status(Response.Status.NOT_FOUND).entity(res))
                .build();
    }
}
