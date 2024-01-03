package mx.com.aey.user.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
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
    private String email;

    @JsonProperty
    @Schema(readOnly = true)
    private String backupEmail;

    @JsonProperty
    @Schema(readOnly = true)
    @Size(min=10, max=10)
    private String phoneNumber;

    @JsonProperty
    @Schema(readOnly = true)
    private Date birthdate;

    @JsonProperty
    @Schema(readOnly = true)
    private Boolean isActive;

    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .isActive(entity.getIsActive())
                .build();
    }

}
