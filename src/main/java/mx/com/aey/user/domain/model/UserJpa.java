package mx.com.aey.user.domain.model;

import java.util.Date;
import jakarta.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tusr01_user")
public class UserJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Basic
    @Column(name = "usr_first_name")
    private String firstName;

    @Basic
    @Column(name = "usr_last_name")
    private String lastName;

    @Basic
    @Column(name = "usr_email")
    private String email;

    @Basic
    @Column(name = "usr_backup_email")
    private String backupEmail;

    @Basic
    @Column(name = "usr_password")
    private String password;

    @Basic
    @Column(name = "usr_phone_number")
    private String phoneNumber;

    @Basic
    @Column(name = "usr_birthdate")
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

    public UserJpa toEntity() {
        return UserJpa.builder()
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

