package mx.com.aey.auth.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import mx.com.aey.auth.domain.entity.SignUp;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "SignUp")
public class SignUpDto {
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
    private Boolean isActive;

    public SignUp toEntity() {
        return SignUp.builder()
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
