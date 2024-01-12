package mx.com.aey.user.domain.enums;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN_USER_ROLE(1),
    GENERAL_USER_ROLE(2),
    EXTERNAL_USER_ROLE(3);

    private final Integer roleId;

    UserRoles (Integer roleId) {
        this.roleId = roleId;
    }
}
