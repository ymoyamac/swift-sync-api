package mx.com.aey.user.infrastructure.rest.dto;

import java.util.Date;
import jakarta.validation.constraints.*;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import mx.com.aey.user.domain.entity.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import mx.com.aey.user.infrastructure.persistence.model.UserJpa;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "CreateUser")
public class CreateUserDto {

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String firstName;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String lastName;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String nickName;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @JsonProperty
    @Schema(required = true)
    @Email
    private String backupEmail;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min=10, max=10)
    @Positive
    private String phoneNumber;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @PastOrPresent
    private Date birthdate;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private Boolean isActive = Boolean.TRUE;

    public User toEntity() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .nickName(nickName)
                .email(email)
                .backupEmail(backupEmail)
                .password(password)
                .phoneNumber(phoneNumber)
                .birthdate(birthdate)
                .isActive(isActive)
                .build();
    }
}
