package com.banco.demosoporte.service.exception;

public class ExchangeNotFoundException extends RuntimeException{
    public ExchangeNotFoundException(String message) {
        super(message);
    }
}
