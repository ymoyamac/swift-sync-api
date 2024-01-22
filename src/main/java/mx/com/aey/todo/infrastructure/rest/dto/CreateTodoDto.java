package mx.com.aey.todo.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mx.com.aey.todo.domain.entity.Todo;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "CreateTodo")
public class CreateTodoDto {

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    @JsonProperty
    @Schema(nullable = true)
    private String description;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .description(description)
                .build();
    }
}
