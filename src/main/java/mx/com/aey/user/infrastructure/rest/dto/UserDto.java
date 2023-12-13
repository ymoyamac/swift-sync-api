package mx.com.aey.user.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import mx.com.aey.user.domain.entity.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "User")
public class UserDto {

    @JsonProperty
    @Schema(readOnly = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String firstName;

    @JsonProperty
    @Schema(readOnly = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String lastName;

    @JsonProperty
    @Schema(readOnly = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @JsonProperty
    @Schema(readOnly = true)
    @Email
    private String backupEmail;

    @JsonProperty
    @Schema(readOnly = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @JsonProperty
    @Schema(readOnly = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min=10, max=10)
    private String phoneNumber;

    @JsonProperty
    @Schema(readOnly = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private Date birthdate;

    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .build();
    }

    public User toEntity() {
        return User.builder()
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
