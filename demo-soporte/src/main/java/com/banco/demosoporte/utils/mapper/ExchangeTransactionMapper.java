package com.banco.demosoporte.utils.mapper;

import com.banco.demosoporte.persistence.entity.ExchangeTransaction;
import com.banco.demosoporte.presentation.dto.response.ExchangeTransactionResponse;

public class ExchangeTransactionMapper {

    public static ExchangeTransactionResponse toResponse(ExchangeTransaction exchangeTransaction) {
        return new ExchangeTransactionResponse(
                exchangeTransaction.getId(),
                exchangeTransaction.getUsername(),
                exchangeTransaction.getInitialAmount(),
                exchangeTransaction.getFinalAmount(),
                exchangeTransaction.getExchangeRate(),
                exchangeTransaction.getFromCurrency(),
                exchangeTransaction.getToCurrency(),
                exchangeTransaction.getTransactionDate()
        );
    }
}
