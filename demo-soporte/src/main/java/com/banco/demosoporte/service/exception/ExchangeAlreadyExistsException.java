package com.banco.demosoporte.service.exception;

public class ExchangeAlreadyExistsException extends RuntimeException {
    public ExchangeAlreadyExistsException(String message) {
        super(message);
    }
}
