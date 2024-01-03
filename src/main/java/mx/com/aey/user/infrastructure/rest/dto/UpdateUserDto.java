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
@Schema(name = "UpdateUser")
public class UpdateUserDto {

    @JsonProperty
    @Schema(readOnly = true)
    @NotBlank
    @NotEmpty
    @NotNull
    private String firstName;

    @JsonProperty
    @Schema(readOnly = true)
    @NotBlank
    @NotEmpty
    @NotNull
    private String lastName;

    @JsonProperty
    @Schema(readOnly = true)
    @NotBlank
    @NotEmpty
    @NotNull
    @Email
    private String email;

    @JsonProperty
    @Schema(readOnly = true)
    @NotBlank
    @NotEmpty
    @NotNull
    @Email
    private String backupEmail;

    @JsonProperty
    @Schema(readOnly = true)
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min=10, max=10)
    @Positive
    private String phoneNumber;

    @JsonProperty
    @Schema(readOnly = true)
    @NotBlank
    @NotEmpty
    @NotNull
    @PastOrPresent
    private Date birthdate;

    public User toEntity() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .backupEmail(backupEmail)
                .phoneNumber(phoneNumber)
                .birthdate(birthdate)
                .build();
    }
}
