package br.com.mh.exception;

import br.com.mh.vo.ApiExceptionDetailsVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiExceptionDetailsVo> handleAuthException(AuthException authException) {

        ApiExceptionDetailsVo details = ApiExceptionDetailsVo.builder()
                .message(authException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(authException.getHttpStatus()).body(details);
    }
}
