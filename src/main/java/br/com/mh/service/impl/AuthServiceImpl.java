package br.com.mh.service.impl;

import br.com.mh.model.AuthUser;
import br.com.mh.model.Role;
import br.com.mh.security.util.JwtUtil;
import br.com.mh.service.AuthService;
import br.com.mh.service.AuthUserService;
import br.com.mh.service.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthUserService authUserService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthUserService authUserService, RoleService roleService, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtil jwtUtil) {

        this.authUserService = authUserService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String authenticateUser(AuthUser authUser) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authUser.getNomeUsuario(), authUser.getPassword())
            );

            return jwtUtil.generateToken((AuthUser) authentication.getPrincipal());
        } catch (Exception e) {

            throw new RuntimeException("Erro ao se autenticar.");
        }
    }

    @Override
    public AuthUser registerUser(AuthUser authUser) {

        Role role = roleService.getRoleByNome(Role.builder().nome("ADMIN").build());

        authUser.setRoles(Collections.singletonList(role));
        authUser.setSenha(passwordEncoder.encode(authUser.getPassword()));

        return authUserService.addAuthUser(authUser);
    }
}
