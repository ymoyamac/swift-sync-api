package mx.com.aey.todo.domain.enums;

import lombok.Getter;

@Getter
public enum TodoStatusRoles {

    CREATED(1, "CREATED"),
    PENDING(2, "PENDING"),
    WAITING(3, "WAITING"),
    FINISHED(4, "FINISHED")
    ;

    private final Integer todoIdStatus;
    private final String todoNameStatus;
    TodoStatusRoles(Integer todoIdStatus, String todoNameStatus) {
        this.todoIdStatus = todoIdStatus;
        this.todoNameStatus = todoNameStatus;
    }
}
