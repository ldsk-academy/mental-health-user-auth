package br.com.mh.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProviderImpl implements JwtProvider {

    private static final int SEGUNDOS = 60;
    private static final int MINUTOS = 60 * SEGUNDOS;
    private static final int JWT_EXPIRATION_HOURS = 2 * MINUTOS;

    private static final String BEARER_PREFIX = "Bearer ";

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    @Override
    public String generateToken(String username) {

        Date expirateDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION_HOURS);

        return Jwts.builder()
                .signWith(secretKey)
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirateDate)
                .compact();
    }

    @Override
    public String getUsernameFromJwt(String token) {

        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    @Override
    public boolean validateToken(String token) {

        try {

            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

            return true;
        } catch (JwtException e) {

            return false;
        }
    }

    @Override
    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization");

        if(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {

            return token.replace(BEARER_PREFIX, "");
        }

        return null;
    }
}
