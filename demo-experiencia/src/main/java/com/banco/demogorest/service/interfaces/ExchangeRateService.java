package com.banco.demogorest.service.interfaces;

import com.banco.demogorest.client.dto.response.ExchangeTransactionResponse;
import com.banco.demogorest.presentation.dto.request.ExchangeRateTransactionRequest;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {
    Mono<ExchangeTransactionResponse> processExchange(Long userId, ExchangeRateTransactionRequest request);
}
