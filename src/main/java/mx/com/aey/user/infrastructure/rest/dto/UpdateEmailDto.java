package mx.com.aey.user.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mx.com.aey.user.domain.entity.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "UpdateEmail")
public class UpdateEmailDto {

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @JsonProperty
    @Schema(nullable = true)
    @Email
    private String backupEmail;

    public static UpdateEmailDto fromEntity(User entity) {
        return UpdateEmailDto.builder()
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .backupEmail(backupEmail)
                .build();
    }
}
