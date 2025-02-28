package com.banco.demosoporte.persistence.repository;

import com.banco.demosoporte.persistence.entity.ExchangeRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ExchangeRateReactiveRepository extends ReactiveCrudRepository<ExchangeRate, Long> {

    Mono<ExchangeRate> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
