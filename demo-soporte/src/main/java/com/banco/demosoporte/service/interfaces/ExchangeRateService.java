package com.banco.demosoporte.service.interfaces;

import com.banco.demosoporte.presentation.dto.request.ExchangeRateRequest;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demosoporte.presentation.dto.response.ExchangeRateResponse;
import com.banco.demosoporte.presentation.dto.response.ExchangeTransactionResponse;
import reactor.core.publisher.Mono;



public interface ExchangeRateService {
    Mono<ExchangeTransactionResponse> processExchange(ExchangeRateTransactionRequest request);
    Mono<ExchangeRateResponse> createExchangeRate(ExchangeRateRequest request);
    Mono<ExchangeRateResponse> updateExchangeRate(ExchangeRateRequest request);
    Mono<ExchangeRateResponse> getExchangeRate(String sourceCurrency, String targetCurrency);
}
