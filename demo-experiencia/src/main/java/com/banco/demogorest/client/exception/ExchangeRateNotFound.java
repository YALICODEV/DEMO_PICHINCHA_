package com.banco.demogorest.client.exception;

public class ExchangeRateNotFound extends RuntimeException {
    public ExchangeRateNotFound(String message) {
        super(message);
    }
}
