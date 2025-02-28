package com.banco.demogorest.config.security;

import com.banco.demogorest.config.security.exception.InvalidTokenException;
import com.banco.demogorest.persistence.entity.User;
import com.banco.demogorest.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.getClaims(auth.getCredentials().toString()))
                .log()
                .onErrorResume(e -> Mono.error(new InvalidTokenException("Invalid or expired token", e)))
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        User.builder()
                                .id(claims.get("id", Long.class))
                                .name(claims.getSubject())
                                .build(),
                        null,
                        List.of()
                ));
    }
}
