package mx.com.aey.user.application.implementation;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;
import mx.com.aey.util.schema.ResponseCode;

import java.lang.reflect.Method;
import java.util.*;

@ApplicationScoped
public class UserBs implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public Either<ErrorCode, List<User>> getUsers(Integer limit, Integer offset) {
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;
        var users = userRepository.findAll(limit, offset);
        if (users.isEmpty()) {
            return Either.right(new ArrayList<User>());
        }
        return Either.right(users);
    }

    @Override
    public Either<ErrorCode, User> getUserById(UUID userId) {
        return userRepository.findOneById(userId)
                .<Either<ErrorCode, User>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.NOT_FOUND));
    }

    @Override
    public Either<ErrorCode, User> getUserByEmail(String userEmail) {
        return userRepository.findOneByEmail(userEmail)
                .<Either<ErrorCode, User>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional
    public Either<ErrorCode, User> create(User user) {
        var error = validateNotNullBlankValues(user);
        if (error != null) {
            return Either.left(error);
        }
        Optional<User> userFound = userRepository.findOneByEmail(user.getEmail());
        if (userFound.isPresent()) {
            return Either.left(ErrorCode.UNIQUENESS_RULE);
        } else {
            var password = BcryptUtil.bcryptHash(user.getPassword());
            user.setPassword(password);
            System.out.println(user.getPassword());
            User userCreated = userRepository.save(user);
            return Either.right(userCreated);
        }
    }

    @Override
    public Either<ErrorCode, User> update(UUID userId, User userTo) {
        var userFound = userRepository.findOneById(userId);
        if (userFound.isPresent()) {
            User user = userFound.get();
            user.setFirstName(userTo.getFirstName() != null ? userTo.getFirstName() : user.getFirstName());
            user.setLastName(userTo.getLastName() != null ? userTo.getLastName() : user.getLastName());
            user.setPhoneNumber(userTo.getPhoneNumber() != null ? userTo.getPhoneNumber() : user.getPhoneNumber());
            user.setBirthdate(userTo.getBirthdate() != null ? userTo.getBirthdate() : user.getBirthdate());
            user.setEmail(user.getEmail());
            user.setBackupEmail(user.getBackupEmail());
            user.setPassword(user.getPassword());
            user.setIsActive(user.getIsActive());
            Optional<User> userUpdated = userRepository.update(user);
            return userUpdated
                    .<Either<ErrorCode, User>>map(Either::right)
                    .orElseGet(() -> Either.left(ErrorCode.BAD_REQUEST));
        }
        return Either.left(ErrorCode.NOT_FOUND);
    }

    @Override
    public Either<ErrorCode, ResponseCode> delete(UUID userId) {
        var userFound = userRepository.findOneById(userId);
        if (userFound.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        var user = userFound.get();
        userRepository.delete(user.getUserId());
        return Either.right(ResponseCode.DELETED);
    }

    private ErrorCode validateNotNullBlankValues(User user) {
        var userClass = user.getClass();
        var values = userClass.getDeclaredMethods();
        for (Method method : values) {
            if (method.getName().startsWith("get") && !method.getName().equals("getUserId")) {
                try {
                    Object value = method.invoke(user);
                    if (value == "") {
                        return ErrorCode.NOT_BLANK_VALUE;
                    }
                    if (value == null) {
                        return ErrorCode.NOT_NULL_VALUE;
                    }
                } catch (Exception e) {
                    return ErrorCode.INTERNATIONAL_SERVER_ERROR;
                }
            }
        }
        return null;
    }
}
