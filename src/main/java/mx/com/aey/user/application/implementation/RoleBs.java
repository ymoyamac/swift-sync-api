package mx.com.aey.user.application.implementation;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.user.domain.repository.RoleRepository;
import mx.com.aey.user.domain.service.RoleService;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class RoleBs implements RoleService {

    @Inject
    RoleRepository roleRepository;

    @Override
    public Set<Role> getUserRolesByUserId(UUID userId) {
        return roleRepository.getUserRolesByUserId(userId)
                .orElseGet(HashSet::new);
    }

    @Override
    public Either<ErrorCode, List<Role>> getRoles() {
        return roleRepository.getRoles()
                .<Either<ErrorCode, List<Role>>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    public Either<ErrorCode, Role> create(Role role) {
        var roleFound = roleRepository.getRoleById(role.getRoleId());
        if (roleFound.isPresent()) {
            return Either.left(ErrorCode.UNIQUENESS_RULE);
        }
        role.setIsActive(Boolean.TRUE);
        return roleRepository.create(role)
                .<Either<ErrorCode, Role>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    public Either<ErrorCode, Role> getRoleById(Integer roleId) {
        return roleRepository.getRoleById(roleId)
                .<Either<ErrorCode, Role>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.NOT_FOUND));
    }
}