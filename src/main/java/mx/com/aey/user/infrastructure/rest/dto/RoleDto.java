package mx.com.aey.user.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import mx.com.aey.user.domain.entity.Role;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
public class RoleDto {

    @JsonProperty
    @Schema(readOnly = true)
    private Integer roleId;
    @JsonProperty
    @Schema(readOnly = true)
    private String roleName;

    @JsonProperty
    @Schema(readOnly = true)
    private Boolean isActive;

    public static RoleDto fromEntity(Role role) {
        return RoleDto.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .isActive(role.getIsActive())
                .build();
    }

    public Role toEntity() {
        return Role.builder()
                .roleId(roleId)
                .roleName(roleName)
                .isActive(isActive)
                .build();
    }
}
