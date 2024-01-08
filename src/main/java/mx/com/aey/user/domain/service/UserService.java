package mx.com.aey.user.domain.service;

import io.vavr.control.Either;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.util.error.ErrorCode;
import mx.com.aey.util.schema.ResponseCode;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Either<ErrorCode, List<User>> getUsers(Integer limit, Integer offset);
    Either<ErrorCode, User> getUserById(UUID userId);
    Either<ErrorCode, User> getUserByEmail(String userEmail);
    Either<ErrorCode, User> create(User user);
    Either<ErrorCode, User> update(UUID userId, User user);
    Either<ErrorCode, ResponseCode> delete(UUID userId);
    Either<ErrorCode, User> updateEmail(UUID userId, User user);
    Either<ErrorCode, ResponseCode> disableUser(UUID userId);

}
