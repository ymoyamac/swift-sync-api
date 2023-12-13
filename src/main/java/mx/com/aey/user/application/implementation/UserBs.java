package mx.com.aey.user.application.implementation;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;

import java.util.UUID;

@ApplicationScoped
public class UserBs implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public Either<ErrorCode, User> getUserById(UUID userId) {
        return userRepository.findById(userId)
                .<Either<ErrorCode, User>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional
    public Either<ErrorCode, User> create(User user) {
        User userCreated = userRepository.save(user);
        return Either.right(userCreated);
    }
}
