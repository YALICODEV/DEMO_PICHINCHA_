package com.banco.demogorest.service.exception;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException(String message) {
        super(message);
    }
}
