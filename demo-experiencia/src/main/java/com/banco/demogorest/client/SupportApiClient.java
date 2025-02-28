package com.banco.demogorest.client;

import com.banco.demogorest.client.dto.request.ExchangeRecordRequest;
import com.banco.demogorest.client.dto.response.ExchangeTransactionResponse;
import com.banco.demogorest.client.exception.ExchangeRateNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SupportApiClient {

    private final WebClient webClient;

    public SupportApiClient(@Qualifier("supportWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ExchangeTransactionResponse> registerExchange(ExchangeRecordRequest request) {
        return webClient.post()
                .uri("/exchange-rate/transaction")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new ExchangeRateNotFound("Exchange rate not found"))
                )
                .bodyToMono(ExchangeTransactionResponse.class);
    }

}
