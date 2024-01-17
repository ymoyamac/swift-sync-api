package mx.com.aey.user.infrastructure.persistence.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.user.domain.repository.RoleRepository;
import mx.com.aey.user.infrastructure.persistence.model.RoleJpa;
import mx.com.aey.user.infrastructure.persistence.query.RoleQuery;
import mx.com.aey.user.infrastructure.persistence.repository.RoleJpaRepository;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleDao implements RoleRepository {

    @Inject
    EntityManager entityManager;
    @Inject
    RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Set<Role>> getUserRolesByUserId(UUID userId) {
        try {
            List<Object[]> resultList = entityManager
                    .createNativeQuery(RoleQuery.FIND_USER_ROLES_BY_USER_ID)
                    .setParameter(RoleQuery.PARAM_USER_ID, userId)
                    .getResultList();
            if (resultList.isEmpty()) {
                return Optional.of(new HashSet<>());
            }
            Set<Role> roles = new HashSet<>();
            resultList.forEach(roleTo ->
                    roles.add(RoleJpa.builder()
                            .roleId((Integer) roleTo[0])
                            .roleName((String) roleTo[2])
                            .isActive((Boolean) roleTo[1])
                            .build().toEntity()
                    )
            );
            return Optional.of(roles);
        } catch (Error e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<Role>> getRoles() {
        return Optional.of(
                roleJpaRepository
                        .findAll()
                        .stream().map(RoleJpa::toEntity)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Optional<Role> create(Role role) {
        return Optional.of(roleJpaRepository.save(RoleJpa.fromEntity(role)).toEntity());
    }

    @Override
    public Optional<Role> getRoleById(Integer roleId) {
        return roleJpaRepository.findById(roleId).map(RoleJpa::toEntity);
    }

    @Override
    public Optional<Role> assignRoleToUser(UUID userId, Integer roleId) {
        entityManager
                .createNativeQuery(RoleQuery.ASSIGN_ROLE_TO_USER)
                .setParameter(RoleQuery.PARAM_USER_ID, userId)
                .setParameter(RoleQuery.PARAM_ROLE_ID, roleId)
                .executeUpdate();
        var rle = roleJpaRepository.findById(roleId);
        if (rle.isPresent()) {
            Role role = rle.get().toEntity();
            return Optional.of(role);
        }
        return Optional.empty();
    }
}
