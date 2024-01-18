package mx.com.aey.auth.application.implementation;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.aey.auth.domain.entity.AuthJsonWebToken;
import mx.com.aey.auth.domain.entity.AuthResponse;
import mx.com.aey.auth.domain.entity.AuthUser;
import mx.com.aey.auth.domain.service.AuthService;
import mx.com.aey.auth.infrastructure.rest.dto.SignInDto;
import mx.com.aey.auth.infrastructure.rest.dto.SignUpDto;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Arrays;

@ApplicationScoped
public class AuthBs implements AuthService {

    @Inject
    UserService userService;
    @ConfigProperty(name = "com.aey.quarkusjwt.jwt.duration")
    Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Override
    public Either<ErrorCode, AuthResponse> signIn(SignInDto signInDto) {
        var userFound = userService.getUserByEmail(signInDto.getEmail());
        if (userFound.isRight()) {
            User user = userFound.get();
            String password = user.getPassword();
            System.out.println(user.getEmail());
            System.out.println(password);
            Boolean isAuthorized = BcryptUtil.matches(signInDto.getPassword(), password);
            if (isAuthorized.equals(Boolean.FALSE)) {
                return Either.left(ErrorCode.UNAUTHORIZED);
            }
            return getAuthResponses(user);
        }
        return Either.left(ErrorCode.ERROR);
    }

    @Override
    public Either<ErrorCode, AuthResponse> signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .nickName(signUpDto.getNickName())
                .email(signUpDto.getEmail())
                .backupEmail(signUpDto.getBackupEmail())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhoneNumber())
                .birthdate(signUpDto.getBirthdate())
                .build();
        var userCreated = userService.create(user);
        if (userCreated.isLeft()) {
            return Either.left(userCreated.getLeft());
        }
        return getAuthResponses(userCreated.get());
    }

    private Either<ErrorCode, AuthResponse> getAuthResponses(User user) {
        AuthUser authUser = AuthUser.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .backupEmail(user.getBackupEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthdate(user.getBirthdate())
                .roles(user.getRoles())
                .build();
        var roles = user.getRoles();
        try {
            String token = AuthJsonWebToken.generateToken(user, roles, duration, issuer);
            return Either.right(new AuthResponse(token, authUser));
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return Either.left(ErrorCode.ERROR);
        }
    }
}
