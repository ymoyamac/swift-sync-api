package mx.com.aey.user.domain.repository;

import mx.com.aey.user.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID userId);
}
