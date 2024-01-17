package mx.com.aey.user.infrastructure.persistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.infrastructure.persistence.model.UserJpa;
import mx.com.aey.user.infrastructure.persistence.query.UserQuery;
import mx.com.aey.user.infrastructure.persistence.repository.UserJpaRepository;

import java.util.*;

@ApplicationScoped
public class UserDao implements UserRepository {

    @Inject
    EntityManager entityManager;
    @Inject
    UserJpaRepository userJpaRepository;

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        try {
            List<Object[]> result = entityManager
                    .createNativeQuery(UserQuery.USERS_PAGINATION)
                    .setParameter(UserQuery.PARAM_USER_LIMIT, limit)
                    .setParameter(UserQuery.PARAM_USER_OFFSET, offset)
                    .getResultList();
            if (result.isEmpty()) {
                return new ArrayList<>();
            }
            List<User> users = new ArrayList<>();
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
            return users;
        } catch (Error e) {
            return new ArrayList<>();
        }
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
            return getUser(result);
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
    public Optional<User> findOneByNickName(String nickname) {
        try {
            Object[] result = (Object[]) entityManager.createNativeQuery(UserQuery.FIND_USER_BY_NICK_NAME)
                    .setParameter(UserQuery.PARAM_USER_NICK_NAME, nickname)
                    .getSingleResult();
            return getUser(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(UUID userId) {
        userJpaRepository.deleteById(userId);
        userJpaRepository.flush();
    }

    private Optional<User> getUser(Object[] result) {
        User user = User.builder()
                .userId((UUID) result[0])
                .firstName((String) result[1])
                .lastName((String) result[2])
                .nickName((String) result[3])
                .email((String) result[4])
                .backupEmail((String) result[5])
                .password((String) result[6])
                .birthdate((Date) result[7])
                .phoneNumber((String) result[8])
                .isActive((Boolean) result[9])
                .build();
        return Optional.of(user);
    }
}
