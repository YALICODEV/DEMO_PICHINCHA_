package com.banco.demosoporte.service.implementation;

import com.banco.demosoporte.persistence.entity.ExchangeTransaction;
import com.banco.demosoporte.persistence.repository.ExchangeRateReactiveRepository;
import com.banco.demosoporte.persistence.repository.ExchangeTransactionReactiveRepository;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateRequest;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demosoporte.presentation.dto.response.ExchangeRateResponse;
import com.banco.demosoporte.presentation.dto.response.ExchangeTransactionResponse;
import com.banco.demosoporte.service.exception.ExchangeAlreadyExistsException;
import com.banco.demosoporte.service.exception.ExchangeNotFoundException;
import com.banco.demosoporte.service.interfaces.ExchangeRateService;
import com.banco.demosoporte.utils.mapper.ExchangeRateMapper;
import com.banco.demosoporte.utils.mapper.ExchangeTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateReactiveRepository exchangeRateRepository;
    private final ExchangeTransactionReactiveRepository exchangeTransactionRepository;

    @Override
    public Mono<ExchangeTransactionResponse> processExchange(ExchangeRateTransactionRequest request) {
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(request.sourceCurrency(), request.targetCurrency())
                .switchIfEmpty(Mono.error(new ExchangeNotFoundException("Exchange rate not found")))
                .flatMap(exchangeRate -> {
                    ExchangeTransaction exchangeTransaction = ExchangeTransaction.builder()
                            .finalAmount(request.amount().multiply(exchangeRate.getExchangeRate()))
                            .exchangeRate(exchangeRate.getExchangeRate())
                            .fromCurrency(request.sourceCurrency())
                            .toCurrency(request.targetCurrency())
                            .createdBy(request.currentUserId())
                            .username(request.userName())
                            .initialAmount(request.amount())
                            .build();
                    return exchangeTransactionRepository.save(exchangeTransaction);
                })
                .flatMap(exchangeTransaction -> {
                    ExchangeTransactionResponse response = ExchangeTransactionMapper.toResponse(exchangeTransaction);
                    return Mono.just(response);
                });
    }

    @Override
    public Mono<ExchangeRateResponse> createExchangeRate(ExchangeRateRequest request) {

        return exchangeRateRepository.findByFromCurrencyAndToCurrency(request.sourceCurrency(), request.targetCurrency())
                .flatMap(existingRate -> Mono.error(new ExchangeAlreadyExistsException("Exchange rate already exists.")))
                .then(exchangeRateRepository.save(ExchangeRateMapper.toEntity(request)))
                .map(ExchangeRateMapper::toResponse);
    }

    @Override
    public Mono<ExchangeRateResponse> updateExchangeRate(ExchangeRateRequest request) {
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(request.sourceCurrency(), request.targetCurrency())
                .switchIfEmpty(Mono.error(new ExchangeNotFoundException("Exchange rate not found")))
                .flatMap(exchangeRate -> {
                    exchangeRate.setExchangeRate(request.exchangeRate());
                    return exchangeRateRepository.save(exchangeRate);
                })
                .map(ExchangeRateMapper::toResponse);
    }

    @Override
    public Mono<ExchangeRateResponse> getExchangeRate(String sourceCurrency, String targetCurrency) {
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(sourceCurrency, targetCurrency)
                .switchIfEmpty(Mono.error(new ExchangeNotFoundException("Exchange rate not found")))
                .map(ExchangeRateMapper::toResponse);
    }
}
