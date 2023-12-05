package mx.com.aey.user.domain.repository;

import mx.com.aey.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String userId);
}
