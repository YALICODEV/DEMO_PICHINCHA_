package com.banco.demosoporte.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ExchangeRateRequest(

        @JsonProperty("source_currency")
        @NotBlank(message = "Source currency is required")
        @Length(min = 3, max = 3, message = "Currency code must have 3 characters")
        String sourceCurrency,

        @JsonProperty("target_currency")
        @NotBlank(message = "Target currency is required")
        @Length(min = 3, max = 3, message = "Currency code must have 3 characters")
        String targetCurrency,

        @JsonProperty("exchange_rate")
        @DecimalMin(value = "0.0", message = "Exchange rate must be greater than zero")
        BigDecimal exchangeRate
) {
}
