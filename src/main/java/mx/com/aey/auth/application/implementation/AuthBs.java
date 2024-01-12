package mx.com.aey.auth.application.implementation;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import mx.com.aey.auth.domain.entity.AuthJsonWebToken;
import mx.com.aey.auth.domain.entity.AuthResponse;
import mx.com.aey.auth.domain.entity.AuthUser;
import mx.com.aey.auth.domain.service.AuthService;
import mx.com.aey.auth.infrastructure.rest.dto.SignInDto;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;

import java.util.Set;

public class AuthBs implements AuthService {

    @Inject
    UserService userService;

    @Override
    public Either<ErrorCode, AuthResponse> signIn(SignInDto signInDto) {
        var userFound = userService.getUserByEmail(signInDto.getEmail());
        if (userFound.isRight()) {
            String password = userFound.get().getPassword();
            Boolean isAuthorized = BcryptUtil.matches(signInDto.getPassword(), password);
            if (isAuthorized.equals(Boolean.FALSE)) {
                return Either.left(ErrorCode.UNAUTHORIZED);
            }
            AuthUser authUser = AuthUser.fromEntity(userFound.get(), Set.of());
            String token = AuthJsonWebToken.generateToken(userFound.get().getUserId());
            return Either.right(new AuthResponse(token, authUser));
        }
        return Either.left(ErrorCode.ERROR);
    }
}
