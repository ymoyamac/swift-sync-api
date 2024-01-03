package mx.com.aey.user.application.implementation;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        User userCreated = userRepository.save(user);
        return Either.right(userCreated);
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
}
