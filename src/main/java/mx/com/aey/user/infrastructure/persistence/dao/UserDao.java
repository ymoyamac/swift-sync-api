package mx.com.aey.user.infrastructure.persistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.infrastructure.persistence.model.UserJpa;
import mx.com.aey.user.infrastructure.persistence.repository.UserJpaRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserDao implements UserRepository {

    @Inject
    UserJpaRepository userJpaRepository;

    public Optional<User> findById(UUID userId) {
        return userJpaRepository.findById(userId)
                .map(UserJpa::toEntity);
    }

    @Override
    public User save(User User) {
        return userJpaRepository.save(UserJpa.fromEntity(User)).toEntity();
    }
}
