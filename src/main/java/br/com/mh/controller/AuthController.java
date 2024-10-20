package br.com.mh.controller;

import br.com.mh.dto.LoginRequestDto;
import br.com.mh.dto.LoginResponseDto;
import br.com.mh.dto.RegisterRequestDto;
import br.com.mh.dto.RegisterResponseDto;
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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        String tokenJwt = authService.authenticateUser(authUserMapper.toAuthUser(loginRequestDto));

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(tokenJwt));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {

        AuthUser authUser = authService.registerUser(authUserMapper.toAuthUser(registerRequestDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(authUserMapper.toRegisterResponseDto(authUser));
    }

}
