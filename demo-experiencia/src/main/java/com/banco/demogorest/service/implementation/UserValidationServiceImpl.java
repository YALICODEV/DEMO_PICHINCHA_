package com.banco.demogorest.service.implementation;

import com.banco.demogorest.client.GoRestClient;
import com.banco.demogorest.client.dto.response.GoRestUserResponse;
import com.banco.demogorest.service.exception.UserNotFoundException;
import com.banco.demogorest.service.interfaces.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {

    private final GoRestClient goRestClient;

    @Override
    public Mono<String> getUserNameIfExists(Long userId) {
        return goRestClient.getUserById(userId)
                .map(GoRestUserResponse::getName)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with id [" + userId + "] not found")));
    }
}
