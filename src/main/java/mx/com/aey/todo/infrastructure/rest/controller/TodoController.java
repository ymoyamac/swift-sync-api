package mx.com.aey.todo.infrastructure.rest.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import mx.com.aey.todo.domain.service.TodoService;
import mx.com.aey.todo.infrastructure.rest.dto.CreateTodoDto;
import mx.com.aey.todo.infrastructure.rest.dto.UpdateTodoDto;
import mx.com.aey.util.error.ErrorMapper;

import java.util.UUID;

@Path("/todos")
public class TodoController {

    @Inject
    TodoService todoService;

    @GET()
    @Path("/{todoId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GENERIC_ROLE"})
    public Response getTodoById(@PathParam("todoId") UUID todoId) {
        return todoService.getTodoById(todoId)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @GET()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GENERIC_ROLE"})
    public Response getTodosByUserId(@Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return todoService.getAllTodosByUserId(userId)
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GENERIC_ROLE"})
    public Response createTodo(
            @Context SecurityContext securityContext,
            @Valid CreateTodoDto createTodoDto
    ) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return todoService.create(userId, createTodoDto.toEntity())
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

    @PATCH
    @Path("/{todoId}")
    @RolesAllowed({"GENERIC_ROLE"})
    public Response updateTodo(
            @PathParam("todoId") UUID todoId,
            @Valid UpdateTodoDto updateTodoDto
    ) {
        return todoService.update(todoId, updateTodoDto.toEntity())
                .map(Response::ok)
                .getOrElseGet(ErrorMapper::toResponse)
                .build();
    }

}
