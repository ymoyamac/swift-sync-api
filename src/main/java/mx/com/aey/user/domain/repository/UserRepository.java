package mx.com.aey.user.domain.repository;

import mx.com.aey.user.domain.entity.User;
import mx.com.aey.util.schema.ResponseCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    List<User> findAll(Integer limit, Integer offset);
    Optional<User> findOneById(UUID userId);
    Optional<User> findOneByEmail(String userEmail);
    User save(User user);
    Optional<User> update(User user);
    void delete(UUID userId);
}
