package mx.com.aey.auth.domain.entity;

import lombok.*;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.user.domain.entity.User;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthUser {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String backupEmail;
    private String phoneNumber;
    private Date birthdate;
    private Set<Role> roles;

    public static AuthUser fromEntity(User entity, Set<Role> roles) {
        return AuthUser.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .roles(roles)
                .build();
    }
}
