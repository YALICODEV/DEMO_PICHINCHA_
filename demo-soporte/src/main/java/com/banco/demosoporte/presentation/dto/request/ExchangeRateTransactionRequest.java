package com.banco.demosoporte.presentation.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExchangeRateTransactionRequest(

        @NotNull(message = "currentUserId is required")
        Long currentUserId,

        @NotBlank(message = "sourceCurrency is required")
        String sourceCurrency,

        @NotBlank(message = "targetCurrency is required")
        String targetCurrency,

        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.0", message = "amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "exchangeRate is required")
        @DecimalMin(value = "0.0", message = "exchangeRate must be greater than 0")
        BigDecimal exchangeRate,

        @NotBlank(message = "userName is required")
        String userName
) {
}
