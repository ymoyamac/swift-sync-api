package mx.com.aey.todo.domain.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoStatusRole {
    private Integer id;
    private String name;
    private Boolean isActive;
}
