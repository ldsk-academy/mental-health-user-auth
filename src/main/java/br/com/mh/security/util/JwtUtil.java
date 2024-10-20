package br.com.mh.security.util;

import br.com.mh.vo.RolesVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final int SEGUNDOS = 60;
    private static final int MINUTOS = 60 * SEGUNDOS;
    private static final int JWT_EXPIRATION_HOURS = 2 * MINUTOS;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String PREFIX_ROLE = "ROLE_";

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles", getRoleNameList(userDetails))
                    .signWith(secretKey)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_HOURS))
                .compact();
    }

    private List<String> getRoleNameList(UserDetails userDetails) {

        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public String getSubjectFromJwt(String token) {

        return extractAllClaims(token).getSubject();
    }

    public List<String> getRolesFromJwt(String token) {

        return extractAllClaims(token).get("roles", RolesVo.class)
                .getRoles()
                .stream()
                .map(this::formatRoleName)
                .collect(Collectors.toList());
    }

    private String formatRoleName(String role) {

        return PREFIX_ROLE.concat(role);
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
