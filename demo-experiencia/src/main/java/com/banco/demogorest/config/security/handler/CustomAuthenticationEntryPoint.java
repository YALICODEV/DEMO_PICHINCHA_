package com.banco.demogorest.config.security.handler;

import com.banco.demogorest.presentation.advice.model.HttpErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        HttpErrorResponse errorResponse = HttpErrorResponse.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized - Authentication is required")
            .build();

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
