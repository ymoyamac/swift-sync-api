package mx.com.aey.user.infrastructure.rest.controllers;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.user.infrastructure.rest.dto.CreateUserDto;
import mx.com.aey.user.infrastructure.rest.dto.UpdateUserDto;
import mx.com.aey.user.infrastructure.rest.dto.UserDto;
import mx.com.aey.util.error.ErrorMapper;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.UUID;
import java.util.stream.Collectors;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;

    @GET
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset) {
        return userService.getUsers(limit, offset)
                .map(users -> Response.ok(users.stream().map(UserDto::fromEntity).collect(Collectors.toList())))
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @GET
    @Path("/user/{userId}")
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") UUID userId) {
        return userService.getUserById(userId)
                .map(user -> Response.ok(UserDto.fromEntity(user)))
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @GET
    @Path("/{userEmail}")
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("userEmail") String userEmail) {
        return userService.getUserByEmail(userEmail)
                .map(user -> Response.ok(UserDto.fromEntity(user)))
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @POST
    @APIResponse(
            responseCode = "200",
            description = "Resource created successfully",
            content = @Content(schema = @Schema(implementation = CreateUserDto.class))
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

    @PUT
    @Path("/user/{userId}")
    @APIResponse(
            responseCode = "200",
            description = "Resource updated successfully",
            content = @Content(schema = @Schema(implementation = UpdateUserDto.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format. Please check the request body"
    )
    @APIResponse(
            responseCode = "404",
            description = "Resource not found"
    )
    public Response updateUser(
            @PathParam("userId") UUID userId,
            @Valid UpdateUserDto updateUserDto
    ) {
        return userService.update(userId, updateUserDto.toEntity())
                .map(user -> Response.ok(UserDto.fromEntity(user)))
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }
}
