package mx.com.aey.user.domain.repository;

import mx.com.aey.user.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<User> findAll(Integer limit, Integer offset);
    Optional<User> findOneById(UUID userId);
    User save(User user);
}
