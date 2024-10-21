package br.com.mh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiExceptionDetailsVo {

    private String message;
    private LocalDateTime timestamp;
}
