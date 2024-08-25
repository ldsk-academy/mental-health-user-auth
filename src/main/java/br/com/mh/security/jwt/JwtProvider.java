package br.com.mh.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtProvider {

    String generateToken(String username);
    String getUsernameFromJwt(String token);
    boolean validateToken(String token);
    String getJwtFromRequest(HttpServletRequest httpServletRequest);
}
