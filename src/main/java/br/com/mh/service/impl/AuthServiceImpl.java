package br.com.mh.service.impl;

import br.com.mh.exception.AuthException;
import br.com.mh.model.AuthUser;
import br.com.mh.security.util.JwtUtil;
import br.com.mh.service.AuthService;
import br.com.mh.service.AuthUserService;
import br.com.mh.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String DEFAULT_USER_ROLE = "USER";
    private static final String WRONG_CREDENTIALS_MESSAGE = "Usuário ou senha incorreto.";

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

            return jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
        } catch (Exception e) {

            throw new AuthException(HttpStatus.BAD_REQUEST, WRONG_CREDENTIALS_MESSAGE);
        }
    }

    @Override
    public AuthUser registerUser(AuthUser authUser) {

        authUser.addRole(roleService.getRoleByNome(DEFAULT_USER_ROLE));

        authUser.setSenha(passwordEncoder.encode(authUser.getPassword()));

        return authUserService.addAuthUser(authUser);
    }
}
