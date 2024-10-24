package br.com.mh.controller;

import br.com.mh.dto.TokenDto;
import br.com.mh.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class JwtController {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtController(JwtUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Void> validateToken(@RequestBody TokenDto tokenDto) {

        boolean isTokenValid = jwtUtil.validateToken(tokenDto.getToken());

        if(!isTokenValid) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
