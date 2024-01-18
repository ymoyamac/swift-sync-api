package mx.com.aey.user.application.implementation;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.user.domain.entity.User;
import mx.com.aey.user.domain.enums.UserRoles;
import mx.com.aey.user.domain.repository.RoleRepository;
import mx.com.aey.user.domain.repository.UserRepository;
import mx.com.aey.user.domain.service.RoleService;
import mx.com.aey.user.domain.service.UserService;
import mx.com.aey.util.error.ErrorCode;
import mx.com.aey.util.schema.ResponseCode;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserBs implements UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    RoleService roleService;

    @Override
    public Either<ErrorCode, List<User>> getUsers(Integer limit, Integer offset) {
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;
        var users = userRepository.findAll(limit, offset);
        if (users.isEmpty()) {
            return Either.right(new ArrayList<User>());
        }
        var activeUsers = users.stream().map(user -> {
            if (user.getIsActive().equals(Boolean.TRUE)) {
                var roles = roleService.getUserRolesByUserId(user.getUserId());
                user.setRoles(roles);
                return user;
            }
            return null;
        }).collect(Collectors.toList());
        return Either.right(activeUsers);
    }

    @Override
    public Either<ErrorCode, User> getUserById(UUID userId) {
        var result = userRepository.findOneById(userId);
        if (result.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (result.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }
        User user = result.get();
        var roles = roleService.getUserRolesByUserId(user.getUserId());
        user.setRoles(roles);
        return Either.right(user);
    }

    @Override
    public Either<ErrorCode, User> getUserByEmail(String userEmail) {
        return userRepository.findOneByEmail(userEmail)
                .<Either<ErrorCode, User>>map(user -> {
                    if (user.getIsActive().equals(Boolean.FALSE)) {
                        return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
                    }
                    var roles = roleService.getUserRolesByUserId(user.getUserId());
                    user.setRoles(roles);
                    return Either.right(user);
                })
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
        }
        Optional<User> userFoundByNick = userRepository.findOneByNickName(user.getNickName());
        if (userFoundByNick.isPresent()) {
            return Either.left(ErrorCode.UNIQUENESS_RULE);
        }
        var password = BcryptUtil.bcryptHash(user.getPassword());
        user.setPassword(password);
        user.setIsActive(Boolean.TRUE);
        User userCreated = userRepository.save(user);
        var roleAssigned = roleService.getRolesAssigned(userCreated.getUserId(), 2);
        if (roleAssigned.isLeft()) {
            return Either.left(roleAssigned.getLeft());
        }
        userCreated.setRoles(Set.of(roleAssigned.get()));
        return Either.right(userCreated);
    }

    @Override
    public Either<ErrorCode, User> update(UUID userId, User userTo) {
        var userFound = userRepository.findOneById(userId);
        if (userFound.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (userFound.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }
        var user = getUserToUpdate(userTo, userFound);
        Optional<User> userUpdated = userRepository.update(user);
        return userUpdated
                .<Either<ErrorCode, User>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.BAD_REQUEST));
    }

    @Override
    public Either<ErrorCode, ResponseCode> delete(UUID userId) {
        var userFound = userRepository.findOneById(userId);
        if (userFound.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (userFound.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }
        var user = userFound.get();
        userRepository.delete(user.getUserId());
        return Either.right(ResponseCode.DELETED);
    }

    @Override
    public Either<ErrorCode, User> updateEmail(UUID userId, User userEmail) {
        var userFound = userRepository.findOneById(userId);
        if (userFound.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (userFound.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }
        var userEmailExist = userRepository.findOneByEmail(userEmail.getEmail());
        if (userEmailExist.isPresent()) {
            return Either.left(ErrorCode.UNIQUENESS_RULE);
        }
        User user = getUserEmailToUpdate(userEmail, userFound);
        if (user == null) {
            return Either.left(ErrorCode.INTERNATIONAL_SERVER_ERROR);
        }
        var error = validateNotNullBlankValues(user);
        if (error != null) {
            return Either.left(error);
        }
        Optional<User> userEmailUpdated = userRepository.updateEmail(user);
        return userEmailUpdated
                .<Either<ErrorCode, User>>map(Either::right)
                .orElseGet(() -> Either.left((ErrorCode.BAD_REQUEST)));
    }

    @Override
    public Either<ErrorCode, ResponseCode> disableUser(UUID userId) {
        var userFound = userRepository.findOneById(userId);
        if (userFound.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (userFound.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }
        User user = userFound.get();
        user.setIsActive(Boolean.FALSE);
        userRepository.disable(user);
        return Either.right(ResponseCode.DISABLE);
    }

    @Override
    public Either<ErrorCode, User> getUserByNickName(String nickName) {
        return userRepository.findOneByNickName(nickName)
                .<Either<ErrorCode, User>>map(user -> {
                    if (user.getIsActive().equals(Boolean.FALSE)) {
                        return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
                    }
                    var roles = roleService.getUserRolesByUserId(user.getUserId());
                    user.setRoles(roles);
                    return Either.right(user);
                })
                .orElseGet(() -> Either.left(ErrorCode.NOT_FOUND));
    }

    private static User getUserEmailToUpdate(User userEmail, Optional<User> userFound) {
        if (userFound.isPresent()) {
            User user = userFound.get();
            user.setEmail(userEmail.getEmail());
            user.setBackupEmail(userEmail.getBackupEmail() == null ? user.getBackupEmail() : userEmail.getBackupEmail());
            user.setFirstName(user.getFirstName());
            user.setLastName(user.getLastName());
            user.setPhoneNumber(user.getPhoneNumber());
            user.setBirthdate(user.getBirthdate());
            user.setPassword(user.getPassword());
            user.setIsActive(user.getIsActive());
            return user;
        }
        return null;
    }

    private static User getUserToUpdate(User userTo, Optional<User> userFound) {
        if (userFound.isPresent()) {
            var user = userFound.get();
            user.setFirstName(userTo.getFirstName() != null ? userTo.getFirstName() : user.getFirstName());
            user.setLastName(userTo.getLastName() != null ? userTo.getLastName() : user.getLastName());
            user.setPhoneNumber(userTo.getPhoneNumber() != null ? userTo.getPhoneNumber() : user.getPhoneNumber());
            user.setBirthdate(userTo.getBirthdate() != null ? userTo.getBirthdate() : user.getBirthdate());
            user.setEmail(user.getEmail());
            user.setBackupEmail(user.getBackupEmail());
            user.setPassword(user.getPassword());
            user.setIsActive(user.getIsActive());
            return user;
        }
        return null;
    }


    private static ErrorCode validateNotNullBlankValues(User user) {
        var userClass = user.getClass();
        var values = userClass.getDeclaredMethods();
        for (Method method : values) {
            if (method.getName().equals("getRoles") || method.getName().equals("getIsActive")) {
                continue;
            }
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
