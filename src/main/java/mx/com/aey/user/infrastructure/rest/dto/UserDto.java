package mx.com.aey.user.infrastructure.rest.dto;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.Email;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import mx.com.aey.user.domain.entity.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "User")
public class UserDto {
    @JsonProperty
    @Schema(readOnly = true)
    private UUID userId;

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

    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .build();
    }

    public UserDto toEntity() {
        return UserDto.builder()
                .userId(userId)
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
