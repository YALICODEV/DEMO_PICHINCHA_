package com.banco.demogorest.presentation.controllers;

import com.banco.demogorest.presentation.dto.request.LoginRequest;
import com.banco.demogorest.presentation.dto.response.TokenResponse;
import com.banco.demogorest.service.interfaces.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

@WebFluxTest(controllers = AuthController.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Login successful")
    void login_Success() {
        LoginRequest request = new LoginRequest("admin@google.com", "123456");
        TokenResponse tokenResponse = new TokenResponse("fake-jwt-token");

        when(userService.login(any(LoginRequest.class))).thenReturn(Mono.just(tokenResponse));

        webTestClient
                .mutateWith(csrf())
                .mutateWith(mockUser("admin@google.com"))
                .post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("fake-jwt-token");
    }

    @Test
    @DisplayName("Login failure - Invalid credentials")
    void login_Failure_InvalidCredentials() {
        LoginRequest request = new LoginRequest("user@example.com", "wrongpassword");

        when(userService.login(any(LoginRequest.class)))
                .thenReturn(Mono.error(new BadCredentialsException("Invalid credentials")));

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("Login failure - Missing Email")
    void login_Failure_MissingEmail() {
        LoginRequest invalidRequest = new LoginRequest(null, "password123"); // Falta email

        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isUnauthorized();
    }


}