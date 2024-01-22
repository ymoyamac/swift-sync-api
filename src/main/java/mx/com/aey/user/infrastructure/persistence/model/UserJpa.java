package mx.com.aey.user.infrastructure.persistence.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import io.quarkus.security.jpa.Password;

import lombok.*;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.user.domain.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "usr01_users")
public class UserJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "u_first_name")
    private String firstName;

    @Column(name = "u_last_name")
    private String lastName;

    @Column(name = "u_nick_name")
    private String nickName;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_backup_email")
    private String backupEmail;

    @Column(name = "u_password")
    @Password
    private String password;

    @Column(name = "u_phone_number")
    private String phoneNumber;

    @Column(name = "u_birthdate")
    private Date birthdate;

    @Column(name = "u_is_active")
    private Boolean isActive;

    @ManyToMany()
    @JoinTable(
            name = "usr03_roles_users",
            joinColumns = @JoinColumn(name = "user_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", insertable = false, updatable = false)
    )
    private Set<RoleJpa> role;

    public static UserJpa fromEntity(User entity) {
        return UserJpa.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .nickName(entity.getNickName())
                .email(entity.getEmail())
                .backupEmail(entity.getBackupEmail())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .birthdate(entity.getBirthdate())
                .isActive(entity.getIsActive())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
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

