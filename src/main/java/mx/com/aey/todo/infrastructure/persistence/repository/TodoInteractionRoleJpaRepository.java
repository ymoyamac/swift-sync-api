package mx.com.aey.todo.infrastructure.persistence.repository;

import mx.com.aey.todo.infrastructure.persistence.model.TodoInteractionRoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoInteractionRoleJpaRepository extends JpaRepository<TodoInteractionRoleJpa, Integer> {
}
