package br.com.mh.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final int SEGUNDOS = 60;
    private static final int MINUTOS = 60 * SEGUNDOS;
    private static final int JWT_EXPIRATION_HOURS = 2 * MINUTOS;

    private static final String SECRET = "2OC9-B5cWopAxe88xZd1Q8RcznXHniIK4k6Tr2L1zO8=";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles", getRoleNameList(userDetails))
                    .signWith(SECRET_KEY)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_HOURS))
                .compact();
    }

    private List<String> getRoleNameList(UserDetails userDetails) {

        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

}
