package mx.com.aey.todo.domain.repository;

import mx.com.aey.todo.domain.entity.Todo;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TodoRepository {
    Optional<Set<Todo>> findAllByUserId(UUID userId);
    Optional<Todo> findOneById(UUID todoId);
    Optional<Todo> create(Todo todo);
    Optional<Todo> update(Todo todo);
}
