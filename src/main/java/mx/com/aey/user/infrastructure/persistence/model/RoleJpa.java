package mx.com.aey.user.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import mx.com.aey.user.domain.entity.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "usr02_roles")
public class RoleJpa {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_is_active")
    private Boolean isActive;

    public static RoleJpa fromEntity(Role entity) {
        return RoleJpa.builder()
                .roleId(entity.getRoleId())
                .roleName(entity.getRoleName())
                .isActive(entity.getIsActive())
                .build();
    }

    public Role toEntity() {
        return Role.builder()
                .roleId(roleId)
                .roleName(roleName)
                .isActive(isActive)
                .build();
    }

}
