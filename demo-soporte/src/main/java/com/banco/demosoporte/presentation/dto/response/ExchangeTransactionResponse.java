package com.banco.demosoporte.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExchangeTransactionResponse(
        Long id,
        String username,

        @JsonProperty("initial_amount")
        BigDecimal initialAmount,

        @JsonProperty("final_amount")
        BigDecimal finalAmount,

        @JsonProperty("exchange_rate")
        BigDecimal exchangeRate,

        @JsonProperty("from_currency")
        String fromCurrency,

        @JsonProperty("to_currency")
        String toCurrency,

        @JsonProperty("transaction_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime transactionDate
) {
}
