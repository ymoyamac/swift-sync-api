package mx.com.aey.auth.domain.entity;

import io.smallrye.jwt.build.Jwt;
import io.vavr.control.Either;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.util.error.ErrorCode;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

public class AuthJsonWebToken {
    private static final Integer TOKEN_LIFE_TIME = 86400 * 2;

    public static String generateToken(UUID upn) {
        return Jwt.issuer("http://localhost:8080")
                .upn(upn.toString())
                .issuedAt(currentTimeInSecs())
                .groups(new HashSet<>(Arrays.asList("ADMIN_USER_ROLE", "GENERAL_USER_ROLE", "EXTERNAL_USER_ROLE")))
                .expiresIn(currentTimeInSecs() + TOKEN_LIFE_TIME)
                .sign();
    }

    private static Long currentTimeInSecs() {
        return new Date().getTime() / 1000;
    }
}
