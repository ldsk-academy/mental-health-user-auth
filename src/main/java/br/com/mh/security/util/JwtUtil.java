package br.com.mh.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final int SEGUNDOS = 60;
    private static final int MINUTOS = 60 * SEGUNDOS;
    private static final int JWT_EXPIRATION_HOURS = 2 * MINUTOS;

    private static final String BEARER_PREFIX = "Bearer ";

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateToken(String username) {

        return Jwts.builder()
                    .subject(username)
                    .signWith(secretKey)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_HOURS))
                .compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization");

        if(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {

            return token.replace(BEARER_PREFIX, "");
        }

        return null;
    }

}
