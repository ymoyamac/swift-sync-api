package mx.com.aey.todo.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import mx.com.aey.todo.domain.entity.TodoInteractionRole;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tdo02_interaction_roles")
public class TodoInteractionRoleJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "interaction_role_id")
    private Integer id;

    @Column(name = "tir_name")
    private String name;

    @Column(name = "tir_is_active")
    private Boolean isActive;

    public TodoInteractionRole toEntity() {
        return TodoInteractionRole.builder()
                .id(id)
                .name(name)
                .isActive(isActive)
                .build();
    }

}
