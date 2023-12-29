package mx.com.aey.user.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.*;
import mx.com.aey.user.domain.entity.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "UpdateUser")
public class UpdateUserDto {

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

    public static UpdateUserDto fromEntity(User entity) {
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

    public UpdateUserDto toEntity() {
        return UpdateUserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .backupEmail(backupEmail)
                .password(password)
                .phoneNumber(phoneNumber)
                .birthdate(birthdate)
                .build();
    }
}
