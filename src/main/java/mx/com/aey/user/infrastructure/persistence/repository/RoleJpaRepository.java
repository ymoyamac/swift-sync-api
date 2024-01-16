package mx.com.aey.user.infrastructure.persistence.repository;

import mx.com.aey.user.infrastructure.persistence.model.RoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleJpa, Integer> {
}
