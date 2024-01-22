package mx.com.aey.todo.domain.entity;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private UUID todoId;
    private String title;
    private String description;
    private Boolean isCompleted;
    private Date creationAt;
    private Date updateAt;
    private Boolean isActive;
    private UUID userId;
}
