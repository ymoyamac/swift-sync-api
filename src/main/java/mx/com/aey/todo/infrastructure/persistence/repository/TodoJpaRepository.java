package mx.com.aey.todo.infrastructure.persistence.repository;

import mx.com.aey.todo.infrastructure.persistence.model.TodoJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoJpaRepository extends JpaRepository<TodoJpa, UUID> {
}
