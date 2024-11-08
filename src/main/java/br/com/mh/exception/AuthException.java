package br.com.mh.exception;

import br.com.mh.vo.ApiExceptionDetailsVo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class AuthException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ApiExceptionDetailsVo apiExceptionDetailsVo;

    public AuthException(HttpStatus httpStatus, String message) {
        super(message);

        this.httpStatus = httpStatus;
        this.apiExceptionDetailsVo = ApiExceptionDetailsVo.builder()
                    .message(message)
                    .timestamp(LocalDateTime.now())
                .build();
    }

}
