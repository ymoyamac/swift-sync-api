package mx.com.aey.auth.domain.entity;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import mx.com.aey.user.domain.entity.Role;
import mx.com.aey.user.domain.entity.User;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class AuthJsonWebToken {

    public static String generateToken(User user, Set<Role> roles, Long duration, String issuer) throws Exception {
        PrivateKey privateKey = readPrivateKey();
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();
        Set<String> groups = new HashSet<>();
        for (Role role : roles) groups.add(role.getRoleName());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(user.getNickName());
        claimsBuilder.upn(user.getFirstName());
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + duration);
        claimsBuilder.groups(groups);

        return claimsBuilder
                .jws()
                .sign(privateKey);
    }

    private static PrivateKey readPrivateKey() throws Exception {
        try (InputStream contentIS = AuthJsonWebToken.class.getResourceAsStream("/privateKey.pem")) {
            byte[] tmp = new byte[4096];
            assert contentIS != null;
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        }
    }

    private static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    private static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}
