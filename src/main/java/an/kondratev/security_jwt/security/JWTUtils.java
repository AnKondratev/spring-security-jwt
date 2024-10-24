package an.kondratev.security_jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTUtils {

    private final SecretKey SECRET_KEY = new SecretKeySpec(
            "1384".getBytes(StandardCharsets.UTF_8),
            "HmacSHA256");

    private static final long EXPIRATION_TIME = 86400000;

    public boolean validateToken(String token, UserDetails user) {
        String userName = user.getUsername();
        Claims claims = extractClaims(token);
        return userName.equals(claims.getSubject()) && !isTokenExpired(claims);
    }

    public String createToken(UserDetails user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}