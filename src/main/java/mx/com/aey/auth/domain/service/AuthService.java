package mx.com.aey.auth.domain.service;

import io.vavr.control.Either;
import mx.com.aey.auth.domain.entity.AuthResponse;
import mx.com.aey.auth.infrastructure.rest.dto.SignInDto;
import mx.com.aey.util.error.ErrorCode;

public interface AuthService {
    Either<ErrorCode, AuthResponse> signIn(SignInDto signInDto);
}
