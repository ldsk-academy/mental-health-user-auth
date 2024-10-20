package br.com.mh.controller;

import br.com.mh.dto.LoginRequestDto;
import br.com.mh.dto.RegisterRequestDto;
import br.com.mh.mapper.AuthUserMapper;
import br.com.mh.model.AuthUser;
import br.com.mh.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthUserMapper authUserMapper;

    @Autowired
    public AuthController(AuthService authService, AuthUserMapper authUserMapper) {

        this.authUserMapper = authUserMapper;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.status(HttpStatus.OK).body(
                authService.authenticateUser(authUserMapper.toAuthUser(loginRequestDto))
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthUser> register(@RequestBody RegisterRequestDto registerRequestDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.registerUser(authUserMapper.toAuthUser(registerRequestDto)));
    }

}
