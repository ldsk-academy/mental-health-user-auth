package br.com.mh.dto;

import lombok.Getter;

@Getter
public class RegisterRequestDto {

    private String username;
    private String password;
    private String cpf;

}
