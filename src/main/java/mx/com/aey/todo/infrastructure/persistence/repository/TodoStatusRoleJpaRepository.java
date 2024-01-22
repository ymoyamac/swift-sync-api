package mx.com.aey.todo.infrastructure.persistence.repository;

import mx.com.aey.todo.infrastructure.persistence.model.TodoStatusRoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoStatusRoleJpaRepository extends JpaRepository<TodoStatusRoleJpa, Integer> {
}
