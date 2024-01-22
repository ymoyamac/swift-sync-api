package mx.com.aey.todo.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import mx.com.aey.todo.domain.entity.Todo;
import mx.com.aey.user.infrastructure.persistence.model.UserJpa;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tdo01_todos")
public class TodoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "todo_id")
    private UUID todoId;

    @Column(name = "t_title")
    private String title;

    @Column(name = "t_description")
    private String description;

    @Column(name = "t_is_completed")
    private Boolean isCompleted;

    @Column(name = "t_creation_at")
    private Date creationAt;

    @Column(name = "t_update_at")
    private Date updateAt;

    @Column(name = "t_is_active")
    private Boolean isActive;

    @Column(name = "t_user_id")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "t_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserJpa user;

    public static TodoJpa fromEntity(Todo entity) {
        return TodoJpa.builder()
                .todoId(entity.getTodoId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .isCompleted(entity.getIsCompleted())
                .creationAt(entity.getCreationAt())
                .updateAt(entity.getUpdateAt())
                .isActive(entity.getIsActive())
                .userId(entity.getUserId())
                .build();
    }

    public Todo toEntity() {
        return Todo.builder()
                .todoId(todoId)
                .title(title)
                .description(description)
                .isCompleted(isCompleted)
                .creationAt(creationAt)
                .updateAt(updateAt)
                .isActive(isActive)
                .userId(userId)
                .build();
    }
}
