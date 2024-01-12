package mx.com.aey.auth.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "SignIn")
public class SignInDto {
    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @JsonProperty
    @Schema(required = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
}
