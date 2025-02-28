package com.banco.demosoporte.persistence.repository;

import com.banco.demosoporte.persistence.entity.ExchangeTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExchangeTransactionReactiveRepository extends ReactiveCrudRepository<ExchangeTransaction, Long> {
}
