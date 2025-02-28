package com.banco.demogorest.service.implementation;

import com.banco.demogorest.client.SupportApiClient;
import com.banco.demogorest.client.dto.request.ExchangeRecordRequest;
import com.banco.demogorest.client.dto.response.ExchangeTransactionResponse;
import com.banco.demogorest.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demogorest.service.exception.UserNotFoundException;
import com.banco.demogorest.service.interfaces.ExchangeRateService;
import com.banco.demogorest.service.interfaces.UserValidationService;
import com.banco.demogorest.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final UserValidationService userValidationService;
    private final SupportApiClient supportApiClient;

    @Override
    public Mono<ExchangeTransactionResponse> processExchange(Long userId, ExchangeRateTransactionRequest request) {

        return AuthUtils.getCurrentUserId()
                .flatMap(authenticatedUserId -> userValidationService.getUserNameIfExists(userId)
                    .switchIfEmpty(Mono.error(new UserNotFoundException("User with id [" + userId + "] not found")))
                    .flatMap(userName -> {
                        ExchangeRecordRequest exchangeRecordRequest = new ExchangeRecordRequest(
                                authenticatedUserId,
                                request.sourceCurrency(),
                                request.targetCurrency(),
                                request.amount(),
                                request.exchangeRate(),
                                userName
                        );
                        return supportApiClient.registerExchange(exchangeRecordRequest);
                    }));
    }
}
