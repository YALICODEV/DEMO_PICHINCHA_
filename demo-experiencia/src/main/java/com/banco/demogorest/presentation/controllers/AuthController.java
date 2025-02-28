package com.banco.demogorest.presentation.controllers;

import com.banco.demogorest.presentation.advice.model.HttpErrorResponse;
import com.banco.demogorest.presentation.dto.request.LoginRequest;
import com.banco.demogorest.presentation.dto.response.TokenResponse;
import com.banco.demogorest.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operaciones relacionadas con la autenticación de usuarios")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Iniciar sesión en el sistema usando email y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso, se devuelve el token JWT"),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class))
            })
    })
    public Mono<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }


}
