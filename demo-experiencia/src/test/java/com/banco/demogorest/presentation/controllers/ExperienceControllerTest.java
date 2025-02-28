package com.banco.demogorest.presentation.controllers;

import com.banco.demogorest.client.dto.response.ExchangeTransactionResponse;
import com.banco.demogorest.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demogorest.service.exception.UserNotFoundException;
import com.banco.demogorest.service.interfaces.ExchangeRateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

@WebFluxTest(controllers = ExperienceController.class)
@ExtendWith(MockitoExtension.class)
class ExperienceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ExchangeRateService exchangeRateService;

    private final String BASE_URL = "/experience";

    @Test
    @DisplayName("Test process exchange rate success")
    void processExchangeRate_Success() {
        Long userId = 1L;
        ExchangeRateTransactionRequest request = new ExchangeRateTransactionRequest(
                BigDecimal.valueOf(100.00),"USD", "BRL", BigDecimal.valueOf(5.5)
        );

        ExchangeTransactionResponse response = new ExchangeTransactionResponse(
                1L, "John Doe", BigDecimal.valueOf(100.00), BigDecimal.valueOf(550.00),
                BigDecimal.valueOf(5.5), "USD", "EUR", LocalDateTime.now()
        );

        when(exchangeRateService.processExchange(anyLong(), any(ExchangeRateTransactionRequest.class)))
                .thenReturn(Mono.just(response));

        webTestClient.mutateWith(mockUser("John Doe"))
                .mutateWith(csrf())
                .post()
                .uri(BASE_URL + "/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("John Doe")
                .jsonPath("$.initial_amount").isEqualTo(100.00)
                .jsonPath("$.final_amount").isEqualTo(550.00)
                .jsonPath("$.from_currency").isEqualTo("USD")
                .jsonPath("$.to_currency").isEqualTo("EUR");
    }

    @Test
    @DisplayName("Test process exchange rate user not found")
    void processExchangeRate_UserNotFound() {
        Long userId = 999L;

        ExchangeRateTransactionRequest request = new ExchangeRateTransactionRequest(
                BigDecimal.valueOf(100.00), "USD", "BRL", BigDecimal.valueOf(5.5)
        );

        when(exchangeRateService.processExchange(anyLong(), any(ExchangeRateTransactionRequest.class)))
                .thenReturn(Mono.error(new UserNotFoundException("User with id [999] not found")));

        webTestClient.mutateWith(mockUser("John Doe"))
                .mutateWith(csrf())
                .post()
                .uri(BASE_URL + "/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Test process exchange rate bad request (invalid data)")
    void processExchangeRate_BadRequest() {
        Long userId = 1L;

        ExchangeRateTransactionRequest invalidRequest = new ExchangeRateTransactionRequest(
                null, "USD", "BRL", BigDecimal.valueOf(5.5)
        );

        webTestClient.mutateWith(mockUser("John Doe"))
                .mutateWith(csrf())
                .post()
                .uri(BASE_URL + "/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Unprocessable entity");
    }

    @Test
    @DisplayName("Test process exchange rate unauthorized (no authentication)")
    void processExchangeRate_Unauthorized() {
        Long userId = 1L;

        ExchangeRateTransactionRequest request = new ExchangeRateTransactionRequest(
                BigDecimal.valueOf(100.00), "USD", "BRL", BigDecimal.valueOf(5.5)
        );

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri(BASE_URL + "/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();
    }


}