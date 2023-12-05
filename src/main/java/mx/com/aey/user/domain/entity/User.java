package mx.com.aey.user.domain.entity;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String backupEmail;
    private String password;
    private String phoneNumber;
    private Date birthdate;
}
