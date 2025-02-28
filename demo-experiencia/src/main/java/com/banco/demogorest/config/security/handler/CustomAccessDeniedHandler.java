package com.banco.demogorest.config.security.handler;

import com.banco.demogorest.config.web.ObjectMapperConfig;
import com.banco.demogorest.presentation.advice.model.HttpErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        HttpErrorResponse errorResponse = HttpErrorResponse.builder()
            .status(HttpStatus.FORBIDDEN.value())
            .message("Access Denied")
            .build();


        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
