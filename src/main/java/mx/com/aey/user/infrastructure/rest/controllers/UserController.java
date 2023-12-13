package mx.com.aey.user.infrastructure.rest.controllers;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.user.infrastructure.rest.dto.CreateUserDto;
import mx.com.aey.user.infrastructure.rest.dto.UserDto;
import mx.com.aey.util.error.ErrorCode;
import mx.com.aey.util.error.ErrorMapper;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.UUID;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;

    @GET()
    @APIResponse(
            responseCode = "200",
            description = "Operation completed successfully",
            content = @Content(schema = @Schema(implementation = UserDto.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. Please check the request body"
    )
    @APIResponse(
            responseCode = "404",
            description = "Resource not found"
    )
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") UUID userId) {
        return userService.getUserById(userId)
                .map(user -> Response.ok(UserDto.fromEntity(user)))
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @POST
    @APIResponse(
            responseCode = "200",
            description = "Resource created successfully",
            content = @Content(schema = @Schema(implementation = UserDto.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. Please check the request body"
    )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid CreateUserDto createUserDto) {
        return userService.create(createUserDto.toEntity())
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }
}
