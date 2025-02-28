package com.banco.demogorest.presentation.advice.handlers;

import com.banco.demogorest.config.security.exception.InvalidTokenException;
import com.banco.demogorest.presentation.advice.model.HttpErrorResponse;
import com.banco.demogorest.service.exception.BadCredentialException;
import com.banco.demogorest.service.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<HttpErrorResponse> handleInvalidToken(InvalidTokenException ex) {
        log.warn("Invalid token: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build());
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<HttpErrorResponse> handleBadCredentials(BadCredentialException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.warn("Argument type not valid : {}", ex.getMessage());

        return ResponseEntity.badRequest().body(HttpErrorResponse.builder()
                .message("Invalid parameter type")
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<HttpErrorResponse> handleValidationException(WebExchangeBindException ex) {

        Map<String, String> errors = new HashMap<>();
        List<String> generalErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                generalErrors.add(error.getDefaultMessage());
            }
        });

        log.info("Validation error: {}", errors);

        return ResponseEntity.badRequest().body(HttpErrorResponse.builder()
                .message("Unprocessable entity")
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .generalErrors(generalErrors)
                .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.warn("Handled ResponseStatusException: {}", ex.getMessage());

        return ResponseEntity.status(ex.getStatusCode()).body(HttpErrorResponse.builder()
                .message(ex.getReason())
                .status(ex.getStatusCode().value())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleException(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpErrorResponse.builder()
                .message("Internal server error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build());
    }

}
