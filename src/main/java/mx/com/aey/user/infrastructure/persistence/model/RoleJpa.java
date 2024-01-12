package mx.com.aey.user.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

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
}
