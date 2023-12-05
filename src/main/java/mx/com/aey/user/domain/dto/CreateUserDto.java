package mx.com.aey.user.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.*;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import mx.com.aey.user.domain.model.UserJpa;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "CreateUser")
public class CreateUserDto {

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

    public static CreateUserDto fromEntity(UserJpa entity) {
        return CreateUserDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .build();
    }

    public CreateUserDto toEntity() {
        return CreateUserDto.builder()
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
