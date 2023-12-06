package mx.com.aey.user.domain.service;

import io.vavr.control.Either;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.util.error.ErrorCode;

import java.util.UUID;

public interface UserService {
    Either<ErrorCode, User> getUserById(UUID userId);
}
