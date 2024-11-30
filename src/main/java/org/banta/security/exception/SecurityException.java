package org.banta.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SecurityException extends RuntimeException {
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public SecurityException(String message) {
        super(message);
    }
}