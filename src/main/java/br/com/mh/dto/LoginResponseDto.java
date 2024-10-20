package br.com.mh.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    public LoginResponseDto(String accessToken) {

        this.accessToken = accessToken;
        this.tokenType = "Bearer ";
    }

    private final String accessToken;
    private final String tokenType;

}
