package mx.com.aey.auth.infrastructure.rest.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.aey.auth.domain.service.AuthService;
import mx.com.aey.auth.infrastructure.rest.dto.SignInDto;
import mx.com.aey.auth.infrastructure.rest.dto.SignUpDto;
import mx.com.aey.user.infrastructure.rest.dto.UpdateUserDto;
import mx.com.aey.util.error.ErrorMapper;
import mx.com.aey.util.schema.ResponseCode;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/auth")
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/sign-in")
    @APIResponse(
            responseCode = "200",
            description = "Successful operation",
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response signIn(@Valid SignInDto signInDto) {
        return authService.signIn(signInDto)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @POST
    @Path("/sign-up")
    @APIResponse(
            responseCode = "201",
            description = "Resource created successfully",
            content = @Content(schema = @Schema(implementation = ResponseCode.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid request format"
    )
    @APIResponse(
            responseCode = "404",
            description = "Resource not found"
    )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response signUp(@Valid SignUpDto signInDto) {
        return authService.signUp(signInDto)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

}
