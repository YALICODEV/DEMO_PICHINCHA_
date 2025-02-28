package com.banco.demosoporte.presentation.controller;

import com.banco.demosoporte.presentation.dto.request.ExchangeRateRequest;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demosoporte.presentation.dto.response.ExchangeRateResponse;
import com.banco.demosoporte.presentation.dto.response.ExchangeTransactionResponse;
import com.banco.demosoporte.service.interfaces.ExchangeRateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class ExchangeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    @DisplayName("Test process exchange transaction successfully")
    void processExchange_Success() {
        ExchangeRateTransactionRequest request = new ExchangeRateTransactionRequest(
                1L, "USD", "BRL", BigDecimal.valueOf(100.00), BigDecimal.valueOf(5.5), "testUser"
        );

        ExchangeTransactionResponse response = new ExchangeTransactionResponse(
                1L, "testUser", BigDecimal.valueOf(100.00), BigDecimal.valueOf(550.00),
                BigDecimal.valueOf(5.5), "USD", "BRL", null
        );

        when(exchangeRateService.processExchange(request)).thenReturn(Mono.just(response));

        webTestClient.post().uri("/exchange-rate/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeTransactionResponse.class)
                .isEqualTo(response);

        verify(exchangeRateService, times(1)).processExchange(request);
    }

    @Test
    @DisplayName("Test create exchange rate successfully")
    void createExchangeRate_Success() {
        ExchangeRateRequest request = new ExchangeRateRequest("USD", "BRL", BigDecimal.valueOf(5.5));
        ExchangeRateResponse response = new ExchangeRateResponse(1L, "USD", "BRL", BigDecimal.valueOf(5.5));

        when(exchangeRateService.createExchangeRate(request)).thenReturn(Mono.just(response));

        webTestClient.post().uri("/exchange-rate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRateResponse.class)
                .isEqualTo(response);

        verify(exchangeRateService, times(1)).createExchangeRate(request);
    }

    @Test
    @DisplayName("Test update exchange rate successfully")
    void updateExchangeRate_Success() {
        ExchangeRateRequest request = new ExchangeRateRequest("USD", "BRL", BigDecimal.valueOf(6.0));
        ExchangeRateResponse response = new ExchangeRateResponse(1L, "USD", "BRL", BigDecimal.valueOf(6.0));

        when(exchangeRateService.updateExchangeRate(request)).thenReturn(Mono.just(response));

        webTestClient.put().uri("/exchange-rate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRateResponse.class)
                .isEqualTo(response);

        verify(exchangeRateService, times(1)).updateExchangeRate(request);
    }

    @Test
    @DisplayName("Test search exchange rate successfully")
    void searchExchangeRate_Success() {
        ExchangeRateResponse response = new ExchangeRateResponse(1L, "USD", "BRL", BigDecimal.valueOf(5.5));

        when(exchangeRateService.getExchangeRate("USD", "BRL")).thenReturn(Mono.just(response));

        webTestClient.get().uri("/exchange-rate/USD/BRL")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRateResponse.class)
                .isEqualTo(response);

        verify(exchangeRateService, times(1)).getExchangeRate("USD", "BRL");
    }
}