package mx.com.aey.todo.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import mx.com.aey.todo.domain.entity.TodoStatusRole;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tdo03_status_roles")
public class TodoStatusRoleJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_role_id")
    private Integer id;

    @Column(name = "tsr_name")
    private String name;

    @Column(name = "tsr_is_active")
    private Boolean isActive;

    public TodoStatusRole toEntity() {
        return TodoStatusRole.builder()
                .id(id)
                .name(name)
                .isActive(isActive)
                .build();
    }
}
