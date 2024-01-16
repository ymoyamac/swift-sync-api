package mx.com.aey.user.domain.service;

import io.vavr.control.Either;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.util.error.ErrorCode;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService {
    Set<Role> getUserRolesByUserId(UUID userId);
    Either<ErrorCode, List<Role>> getRoles();
    Either<ErrorCode, Role> create(Role role);
    Either<ErrorCode, Role> getRoleById(Integer roleId);
}
