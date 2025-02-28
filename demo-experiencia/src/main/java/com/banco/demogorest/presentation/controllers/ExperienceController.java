package com.banco.demogorest.presentation.controllers;

import com.banco.demogorest.client.dto.response.ExchangeTransactionResponse;
import com.banco.demogorest.presentation.advice.model.HttpErrorResponse;
import com.banco.demogorest.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demogorest.service.interfaces.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/experience")
@RequiredArgsConstructor
@Tag(name = "Experience", description = "Operaciones relacionadas con las transacciones de tipo de cambio")
@SecurityRequirement(name = "bearerAuth")
public class ExperienceController {

    private final ExchangeRateService exchangeRateService;

    @PostMapping("/{userId}")
    @Operation(
            summary = "Procesar transacción de tipo de cambio",
            description = "Este endpoint procesa una transacción de tipo de cambio para un usuario específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transacción procesada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExchangeTransactionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class)))
    })
    public Mono<ExchangeTransactionResponse> processExchangeRate(
            @Valid @RequestBody ExchangeRateTransactionRequest exchangeRateTransactionRequest,
            @PathVariable(name = "userId") Long userId
    ) {
        return exchangeRateService.processExchange(userId, exchangeRateTransactionRequest);
    }
}
