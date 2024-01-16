package mx.com.aey.user.domain.repository;

import mx.com.aey.user.domain.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository {
    Optional<Set<Role>> getUserRolesByUserId(UUID userId);
    Optional<List<Role>> getRoles();
    Optional<Role> create(Role role);
    Optional<Role> getRoleById(Integer roleId);
}
