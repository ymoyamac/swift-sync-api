package mx.com.aey.user.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.Email;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import mx.com.aey.user.domain.model.UserJpa;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "UpdateUser")
public class UpdateUserDto {
    @JsonProperty
    @Schema(readOnly = true)
    private String userId;

    @JsonProperty
    @Schema(readOnly = true)
    private String firstName;

    @JsonProperty
    @Schema(readOnly = true)
    private String lastName;

    @JsonProperty
    @Schema(readOnly = true)
    @Email
    private String email;

    @JsonProperty
    @Schema(readOnly = true)
    @Email
    private String backupEmail;

    @JsonProperty
    @Schema(readOnly = true)
    private String password;

    @JsonProperty
    @Schema(readOnly = true)
    private String phoneNumber;

    @JsonProperty
    @Schema(readOnly = true)
    private Date birthdate;
    public static UpdateUserDto fromEntity(UserJpa entity) {
        return UpdateUserDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .build();
    }
}
