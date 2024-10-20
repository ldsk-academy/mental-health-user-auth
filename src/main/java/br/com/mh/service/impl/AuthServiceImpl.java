package br.com.mh.service.impl;

import br.com.mh.model.AuthUser;
import br.com.mh.security.util.JwtUtil;
import br.com.mh.service.AuthService;
import br.com.mh.service.AuthUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthUserService authUserService, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtil jwtUtil) {

        this.authUserService = authUserService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String authenticateUser(AuthUser authUser) {

        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authUser.getNomeUsuario(), authUser.getPassword())
            );
        } catch (Exception e) {

            throw new RuntimeException("Erro ao se autenticar.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtil.generateToken(authUser);
    }

    @Override
    public AuthUser registerUser(AuthUser authUser) {

        authUser.setSenha(passwordEncoder.encode(authUser.getPassword()));

        return authUserService.addAuthUser(authUser);
    }
}
