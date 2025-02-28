package com.banco.demosoporte.utils.mapper;

import com.banco.demosoporte.persistence.entity.ExchangeRate;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateRequest;
import com.banco.demosoporte.presentation.dto.response.ExchangeRateResponse;

public class ExchangeRateMapper {
    public static ExchangeRateResponse toResponse(ExchangeRate entity) {
        return new ExchangeRateResponse(
                entity.getId(),
                entity.getFromCurrency(),
                entity.getToCurrency(),
                entity.getExchangeRate()
        );
    }

    public static ExchangeRate toEntity(ExchangeRateRequest request) {
        return ExchangeRate.builder()
                .fromCurrency(request.sourceCurrency())
                .toCurrency(request.targetCurrency())
                .exchangeRate(request.exchangeRate())
                .build();
    }
}
