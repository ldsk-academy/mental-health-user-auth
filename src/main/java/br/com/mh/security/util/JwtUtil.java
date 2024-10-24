package br.com.mh.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtUtil {

    private static final int JWT_EXPIRATION_HOURS = 2;
    private static final int JWT_EXPIRATION_MILLIS = JWT_EXPIRATION_HOURS * 60 * 60 * 1000;


    private static final String SECRET = "2OC9-B5cWopAxe88xZd1Q8RcznXHniIK4k6Tr2L1zO8=";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles", getRoleNameList(userDetails))
                    .signWith(SECRET_KEY)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MILLIS))
                .compact();
    }

    private List<String> getRoleNameList(UserDetails userDetails) {

        if(userDetails.getAuthorities() == null) return Collections.emptyList();

        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {

            log.info("Invalid JWT! \nMessage: ".concat(e.getLocalizedMessage()));

            return false;
        }
    }

}
