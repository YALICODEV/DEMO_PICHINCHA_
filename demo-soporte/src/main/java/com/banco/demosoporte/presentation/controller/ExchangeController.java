package com.banco.demosoporte.presentation.controller;

import com.banco.demosoporte.presentation.advice.model.HttpErrorResponse;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateRequest;
import com.banco.demosoporte.presentation.dto.request.ExchangeRateTransactionRequest;
import com.banco.demosoporte.presentation.dto.response.ExchangeRateResponse;
import com.banco.demosoporte.presentation.dto.response.ExchangeTransactionResponse;
import com.banco.demosoporte.service.interfaces.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
@Tag(name = "Exchange Rate", description = "Operaciones relacionadas con las tasas de cambio")
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;

    @PostMapping("/transaction")
    @Operation(
            summary = "Procesar una transacción de intercambio",
            description = "Realiza una transacción de intercambio entre dos divisas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transacción procesada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExchangeTransactionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Solicitud inválida - tipo de cambio no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class)))
            }
    )
    public Mono<ExchangeTransactionResponse> processExchange(@Valid @RequestBody ExchangeRateTransactionRequest request) {
        return exchangeRateService.processExchange(request);
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva tasa de cambio",
            description = "Crea una nueva tasa de cambio entre dos divisas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasa de cambio creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExchangeRateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class)))
            }
    )
    public Mono<ExchangeRateResponse> createExchangeRate(@Valid @RequestBody ExchangeRateRequest request) {
        return exchangeRateService.createExchangeRate(request);
    }

    @PutMapping
    @Operation(
            summary = "Actualizar una tasa de cambio existente",
            description = "Actualiza una tasa de cambio entre dos divisas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasa de cambio actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExchangeRateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class)))
            }
    )
    public Mono<ExchangeRateResponse> updateExchangeRate(@Valid @RequestBody ExchangeRateRequest request) {
        return exchangeRateService.updateExchangeRate(request);
    }

    @GetMapping("/{sourceCurrency}/{targetCurrency}")
    @Operation(
            summary = "Buscar tasa de cambio",
            description = "Busca la tasa de cambio entre dos divisas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasa de cambio encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExchangeRateResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Tasa de cambio no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class)))
            }
    )
    public Mono<ExchangeRateResponse> searchExchangeRate(
            @PathVariable("sourceCurrency") String sourceCurrency,
            @PathVariable("targetCurrency") String targetCurrency
    ) {
        return exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency);
    }
}
