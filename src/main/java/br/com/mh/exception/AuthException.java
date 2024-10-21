package br.com.mh.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public AuthException(HttpStatus httpStatus, String message) {
        super(message);

        this.httpStatus = httpStatus;
        this.message = message;
    }

}
