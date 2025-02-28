package com.banco.demosoporte.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ExchangeRateResponse(
        Long id,
        @JsonProperty("source_currency")
        String sourceCurrency,
        @JsonProperty("target_currency")
        String targetCurrency,
        @JsonProperty("exchange_rate")
        BigDecimal exchangeRate
) {
}
