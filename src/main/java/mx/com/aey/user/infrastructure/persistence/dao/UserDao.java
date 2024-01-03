package mx.com.aey.user.infrastructure.persistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.infrastructure.persistence.model.UserJpa;
import mx.com.aey.user.infrastructure.persistence.repository.UserJpaRepository;

import java.util.*;

@ApplicationScoped
public class UserDao implements UserRepository {

    @Inject
    UserJpaRepository userJpaRepository;
    @Inject
    EntityManager entityManager;


    @Override
    public List<User> findAll(Integer limit, Integer offset) {

        List<Object[]> result = entityManager
                .createNativeQuery(UserQuery.USERS_PAGINATION)
                .setParameter(UserQuery.PARAM_USER_LIMIT, limit)
                .setParameter(UserQuery.PARAM_USER_OFFSET, offset)
                .getResultList();
        List<User> users = new ArrayList<>();

        if (!result.isEmpty()) {
            result.forEach(user -> users.add(
                    User.builder()
                            .userId((UUID) user[0])
                            .firstName((String) user[1])
                            .lastName((String) user[2])
                            .email((String) user[3])
                            .backupEmail((String) user[4])
                            .birthdate((Date) user[5])
                            .phoneNumber((String) user[6])
                            .isActive((Boolean) user[7])
                            .build()
            ));
        } else {
            return new ArrayList<>();
        }
        return users;
    }

    @Override
    public Optional<User> findOneById(UUID userId) {
        return userJpaRepository.findById(userId)
                .map(UserJpa::toEntity);
    }

    @Override
    public Optional<User> findOneByEmail(String userEmail) {
        Object[] result = (Object[]) entityManager.createNativeQuery(UserQuery.FIND_USER_BY_EMAIL)
                .setParameter(UserQuery.PARAM_USER_EMAIL, userEmail)
                .getSingleResult();
        if (result == null) {
            return Optional.empty();
        }
        User user = User.builder()
                .userId((UUID) result[0])
                .firstName((String) result[1])
                .lastName((String) result[2])
                .email((String) result[3])
                .backupEmail((String) result[4])
                .birthdate((Date) result[5])
                .phoneNumber((String) result[6])
                .isActive((Boolean) result[7])
                .build();
        return Optional.of(user);
    }

    @Override
    public User save(User User) {
        return userJpaRepository.save(UserJpa.fromEntity(User)).toEntity();
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.of(userJpaRepository.saveAndFlush(UserJpa.fromEntity(user)).toEntity());
    }
}
