package com.banco.demogorest.service.interfaces;

import reactor.core.publisher.Mono;

public interface UserValidationService {
    Mono<String> getUserNameIfExists(Long userId);
}
