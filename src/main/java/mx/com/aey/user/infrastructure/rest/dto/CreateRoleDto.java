package mx.com.aey.user.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import mx.com.aey.user.domain.entity.Role;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CreateRoleDto {
    @JsonProperty
    @Schema(required = true)
    @NotBlank
    @NotEmpty
    @NotNull
    private String roleName;

    public Role toEntity() {
        return Role.builder()
                .roleName(roleName)
                .build();
    }
}
