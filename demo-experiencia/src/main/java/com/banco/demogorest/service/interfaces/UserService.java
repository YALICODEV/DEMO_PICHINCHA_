package com.banco.demogorest.service.interfaces;

import com.banco.demogorest.presentation.dto.request.LoginRequest;
import com.banco.demogorest.presentation.dto.response.TokenResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<TokenResponse> login(LoginRequest loginRequest);
}
