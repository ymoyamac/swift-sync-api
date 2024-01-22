package mx.com.aey.todo.domain.repository;

import mx.com.aey.todo.domain.entity.Todo;
import mx.com.aey.todo.domain.entity.TodoInteractionRole;
import mx.com.aey.todo.domain.entity.TodoStatusRole;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TodoRepository {
    Optional<Set<Todo>> findAllByUserId(UUID userId);
    Optional<Todo> findOneById(UUID todoId);
    Optional<Todo> create(Todo todo);
    Optional<Todo> update(Todo todo);
    Optional<TodoInteractionRole> getTodoInteractionRole(Integer id);
    Optional<TodoStatusRole> getTodoStatusRole(Integer id);
    void deleteTodo(UUID todoId);
}
