package mx.com.aey.auth.infrastructure.rest.controller;

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
import mx.com.aey.util.error.ErrorMapper;

@Path("/auth")
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/sign-in")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(@Valid SignInDto signInDto) {
        return authService.signIn(signInDto)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @POST
    @Path("/sign-up")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(@Valid SignUpDto signInDto) {
        return authService.signUp(signInDto)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

}
