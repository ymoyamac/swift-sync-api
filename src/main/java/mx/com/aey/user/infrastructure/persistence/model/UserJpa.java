package mx.com.aey.user.infrastructure.persistence.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import mx.com.aey.user.domain.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tusr01_users")
public class UserJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "u_first_name")
    private String firstName;

    @Column(name = "u_last_name")
    private String lastName;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_backup_email")
    private String backupEmail;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_phone_number")
    private String phoneNumber;

    @Column(name = "u_birthdate")
    private Date birthdate;

    public static UserJpa fromEntity(UserJpa entity) {
        return UserJpa.builder()
                .userId(entity.getUserId())
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
                .userId(userId)
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

