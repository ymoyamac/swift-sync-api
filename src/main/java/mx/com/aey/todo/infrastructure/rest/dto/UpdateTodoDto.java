package mx.com.aey.todo.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import mx.com.aey.todo.domain.entity.Todo;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class UpdateTodoDto {

    @JsonProperty
    @Schema(nullable = true)
    private String title;

    @JsonProperty
    @Schema(nullable = true)
    private String description;

    @JsonProperty
    @Schema(nullable = true)
    private Boolean isCompleted;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .description(description)
                .isCompleted(isCompleted)
                .build();
    }
}
