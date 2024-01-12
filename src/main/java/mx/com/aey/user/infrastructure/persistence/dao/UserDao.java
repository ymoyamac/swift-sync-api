package mx.com.aey.user.infrastructure.persistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.infrastructure.persistence.model.UserJpa;
import mx.com.aey.user.infrastructure.persistence.repository.UserJpaRepository;
import mx.com.aey.util.schema.ResponseCode;

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
                    .nickName((String) user[3])
                    .email((String) user[4])
                    .backupEmail((String) user[5])
                    .birthdate((Date) user[6])
                    .phoneNumber((String) user[7])
                    .isActive((Boolean) user[8])
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
        try {
            Object[] result = (Object[]) entityManager.createNativeQuery(UserQuery.FIND_USER_BY_EMAIL)
                    .setParameter(UserQuery.PARAM_USER_EMAIL, userEmail)
                    .getSingleResult();
            User user = User.builder()
                .userId((UUID) result[0])
                .firstName((String) result[1])
                .lastName((String) result[2])
                .nickName((String) result[3])
                .email((String) result[4])
                .backupEmail((String) result[5])
                .birthdate((Date) result[6])
                .phoneNumber((String) result[7])
                .isActive((Boolean) result[8])
                .build();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserJpa.fromEntity(user)).toEntity();
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.of(userJpaRepository.saveAndFlush(UserJpa.fromEntity(user)).toEntity());
    }

    @Override
    public Optional<User> updateEmail(User user) {
        return Optional.of(userJpaRepository.save(UserJpa.fromEntity(user)).toEntity());
    }

    @Override
    public void disable(User user) {
        userJpaRepository.saveAndFlush(UserJpa.fromEntity(user)).toEntity();
        userJpaRepository.flush();
    }

    @Override
    public void delete(UUID userId) {
        userJpaRepository.deleteById(userId);
        userJpaRepository.flush();
    }
}
