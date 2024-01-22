package mx.com.aey.todo.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mx.com.aey.todo.domain.entity.Todo;
import mx.com.aey.todo.domain.entity.TodoInteractionRole;
import mx.com.aey.todo.domain.entity.TodoStatusRole;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "Todo")
public class TodoDto {
    @JsonProperty
    @Schema(readOnly = true)
    private UUID todoId;

    @JsonProperty
    @Schema(readOnly = true)
    private String title;

    @JsonProperty
    @Schema(readOnly = true)
    private String description;

    @JsonProperty
    @Schema(readOnly = true)
    private Boolean isCompleted;

    @JsonProperty
    @Schema(readOnly = true)
    private Date creationAt;

    @JsonProperty
    @Schema(readOnly = true)
    private Date updateAt;

    @JsonProperty
    @Schema(readOnly = true)
    private Boolean isActive;

    @JsonProperty
    @Schema(readOnly = true)
    private UUID userId;

    @JsonProperty
    @Schema(readOnly = true)
    private Integer todoInteractionRoleId;

    @JsonProperty
    @Schema(readOnly = true)
    private Integer todoStatusRoleId;

    public static TodoDto fromEntity(Todo entity) {
        return TodoDto.builder()
                .todoId(entity.getTodoId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .isCompleted(entity.getIsCompleted())
                .creationAt(entity.getCreationAt())
                .updateAt(entity.getUpdateAt())
                .isActive(entity.getIsActive())
                .userId(entity.getUserId())
                .todoInteractionRoleId(entity.getTodoInteractionRoleId())
                .todoStatusRoleId(entity.getTodoStatusRoleId())
                .build();
    }
}
