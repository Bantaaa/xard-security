package org.banta.security.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
}