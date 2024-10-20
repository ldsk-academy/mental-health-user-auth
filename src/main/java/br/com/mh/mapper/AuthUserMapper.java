package br.com.mh.mapper;

import br.com.mh.dto.LoginRequestDto;
import br.com.mh.dto.RegisterRequestDto;
import br.com.mh.dto.RegisterResponseDto;
import br.com.mh.model.AuthUser;
import org.springframework.stereotype.Component;

@Component
public class AuthUserMapper {

    public AuthUser toAuthUser(LoginRequestDto loginRequestDto) {

        return AuthUser.builder()
                    .nomeUsuario(loginRequestDto.getUsername())
                    .senha(loginRequestDto.getPassword())
                .build();
    }

    public AuthUser toAuthUser(RegisterRequestDto registerRequestDto) {

        return AuthUser.builder()
                    .nomeUsuario(registerRequestDto.getUsername())
                    .senha(registerRequestDto.getPassword())
                    .cpf(registerRequestDto.getCpf())
                .build();
    }

    public RegisterResponseDto toRegisterResponseDto(AuthUser authUser) {

        return RegisterResponseDto.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .cpf(authUser.getCpf())
                .build();
    }

}
