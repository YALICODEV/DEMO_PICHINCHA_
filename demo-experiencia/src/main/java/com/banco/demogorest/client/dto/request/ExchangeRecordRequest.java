package com.banco.demogorest.client.dto.request;


import java.math.BigDecimal;

public record ExchangeRecordRequest(Long currentUserId, String sourceCurrency, String targetCurrency, BigDecimal amount,
                                    BigDecimal exchangeRate, String userName) {
}
