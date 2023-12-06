package mx.com.aey.user.infrastructure.persistence.repository;

import mx.com.aey.user.infrastructure.persistence.model.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {
}
