package mx.com.aey.user.infrastructure.rest.controllers;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.com.aey.user.domain.service.RoleService;
import mx.com.aey.user.infrastructure.rest.dto.CreateRoleDto;
import mx.com.aey.user.infrastructure.rest.dto.RoleDto;
import mx.com.aey.util.error.ErrorMapper;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("roles")
public class RoleController {

    @Inject
    RoleService roleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoles() {
        return roleService.getRoles()
            .map(roles -> Response.ok(roles.stream().map(RoleDto::fromEntity).collect(Collectors.toList())))
            .getOrElseGet(ErrorMapper::toResponse)
            .build();
    }

    @GET
    @Path("/user-roles/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserRolesByUserId(@PathParam("userId") UUID userId) {
        var roles = roleService.getUserRolesByUserId(userId);
        if (roles.isEmpty()) {
            return Response.ok(new HashSet<>()).build();
        }
        return Response.ok(roles).status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(@Valid CreateRoleDto createRoleDto) {
        return roleService.create(createRoleDto.toEntity())
                .map(role -> Response.ok(role).status(Response.Status.CREATED))
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @GET
    @Path("/{roleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoleById(@PathParam("roleId")Integer roleId) {
        return roleService.getRoleById(roleId)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

}
