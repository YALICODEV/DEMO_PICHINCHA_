package com.banco.demogorest.client;

import com.banco.demogorest.client.dto.response.GoRestUserResponse;
import com.banco.demogorest.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GoRestClient {

    private final WebClient webClient;

    public GoRestClient(@Qualifier("goRestWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GoRestUserResponse> getUserById(Long id) {
            return webClient.get()
                    .uri("/users/{id}", id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                            return Mono.error(new UserNotFoundException("User with ID " + id + " not found"));
                        }
                        return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new RuntimeException("Server error: " + response.statusCode()))
                    )
                    .bodyToMono(GoRestUserResponse.class);
    }

}
