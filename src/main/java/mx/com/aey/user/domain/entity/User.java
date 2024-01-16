package mx.com.aey.user.domain.entity;

import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String backupEmail;
    private String password;
    private String phoneNumber;
    private Date birthdate;
    private Boolean isActive;
    private Set<Role> roles;
}
