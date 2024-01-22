package mx.com.aey.todo.infrastructure.persistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import mx.com.aey.todo.domain.entity.Todo;
import mx.com.aey.todo.domain.repository.TodoRepository;
import mx.com.aey.todo.infrastructure.persistence.TodoQuery;
import mx.com.aey.todo.infrastructure.persistence.model.TodoJpa;
import mx.com.aey.todo.infrastructure.persistence.repository.TodoJpaRepository;
import mx.com.aey.user.domain.entity.User;

import java.util.*;

@ApplicationScoped
public class TodoDao implements TodoRepository {
    @Inject
    TodoJpaRepository todoJpaRepository;
    @Inject
    EntityManager entityManager;

    @Override
    public Optional<Set<Todo>> findAllByUserId(UUID userId) {
        try {
            List<Object[]> result = entityManager
                    .createNativeQuery(TodoQuery.FIND_TODOS_BY_USER_ID)
                    .setParameter(TodoQuery.PARAM_USER_ID, userId)
                    .getResultList();
            Set<Todo> todos = new HashSet<>();
            result.forEach(todo -> todos.add(
                    Todo.builder()
                            .todoId((UUID) todo[0])
                            .title((String) todo[1])
                            .description((String) todo[2])
                            .isCompleted((Boolean) todo[3])
                            .creationAt((Date) todo[4])
                            .updateAt((Date) todo[5])
                            .isActive((Boolean) todo[6])
                            .userId((UUID) todo[7])
                            .build()
            ));
            return Optional.of(todos);
        } catch (Error e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Todo> findOneById(UUID todoId) {
        return todoJpaRepository.findById(todoId)
                .map(TodoJpa::toEntity);
    }

    @Override
    public Optional<Todo> create(Todo todo) {
        return Optional.of(todoJpaRepository.save(TodoJpa.fromEntity(todo)).toEntity());
    }

    @Override
    public Optional<Todo> update(Todo todo) {
        return Optional.of(todoJpaRepository.saveAndFlush(TodoJpa.fromEntity(todo)).toEntity());
    }
}
