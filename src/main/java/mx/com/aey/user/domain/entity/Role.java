package mx.com.aey.user.domain.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Role {
    private Long roleId;
    private String roleName;
    private Boolean isActive;
}
