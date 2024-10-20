package br.com.mh.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {

    private Long id;
    private String username;
    private String cpf;

}
