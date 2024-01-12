package mx.com.aey.auth.domain.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignIn {
    private String email;
    private String password;
}
