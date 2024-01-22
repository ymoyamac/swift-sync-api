package mx.com.aey.todo.domain.enums;

import lombok.Getter;

@Getter
public enum TodoInteractionRoles {
    OWNER(1, "OWNER"),
    VIEWER(2, "VIEWER"),
    EDITOR(3, "EDITOR")
    ;

    private final Integer todoIdRole;
    private final String todoIdRoleName;

    TodoInteractionRoles(Integer todoIdRole, String todoIdRoleName) {
        this.todoIdRole = todoIdRole;
        this.todoIdRoleName = todoIdRoleName;
    }
}
