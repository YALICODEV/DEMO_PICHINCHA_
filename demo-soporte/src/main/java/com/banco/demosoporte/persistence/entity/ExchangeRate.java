package com.banco.demosoporte.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("exchange_rate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRate {

    @Id
    private Long id;

    @Column("from_currency")
    private String fromCurrency;

    @Column("to_currency")
    private String toCurrency;

    @Column("exchange_rate")
    private BigDecimal exchangeRate;

    @Column(value = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(value = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
