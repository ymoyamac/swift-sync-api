package mx.com.aey.todo.domain.service;

import io.vavr.control.Either;
import mx.com.aey.todo.domain.entity.Todo;
import mx.com.aey.todo.domain.entity.TodoInteractionRole;
import mx.com.aey.todo.domain.entity.TodoStatusRole;
import mx.com.aey.util.error.ErrorCode;
import mx.com.aey.util.schema.ResponseCode;

import java.util.Set;
import java.util.UUID;

public interface TodoService {
    Either<ErrorCode, Set<Todo>> getAllTodosByUserId(UUID userId);
    Either<ErrorCode, Todo> getTodoById(UUID todoId);
    Either<ErrorCode, Todo> create(UUID userId, Todo todo);
    Either<ErrorCode, Todo> update(UUID todoId, Todo todo);
    Either<ErrorCode, ResponseCode> deleteTodoById(UUID todoId);
    Either<ErrorCode, TodoInteractionRole> getTodoInteractionRole(Integer id);
    Either<ErrorCode, TodoStatusRole> getTodoStatusRole(Integer id);
}
