package com.banco.demosoporte.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("exchange_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeTransaction {

    @Id
    private Long id;

    @Column("created_by")
    private Long createdBy;

    private String username;

    @Column("initial_amount")
    private BigDecimal initialAmount;

    @Column("from_currency")
    private String fromCurrency;

    @Column("to_currency")
    private String toCurrency;

    @Column("final_amount")
    private BigDecimal finalAmount;

    @Column("exchange_rate")
    private BigDecimal exchangeRate;

    @Column("transaction_date")
    @CreatedDate
    private LocalDateTime transactionDate;
}
