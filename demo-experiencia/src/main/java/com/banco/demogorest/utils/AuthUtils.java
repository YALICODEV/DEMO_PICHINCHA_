package com.banco.demogorest.utils;

import com.banco.demogorest.persistence.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class AuthUtils {

    public static Mono<Long> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    if(authentication != null && authentication.getPrincipal() instanceof User user) {
                        return Mono.just(user.getId());
                    }
                   return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not authenticated")));
    }
}
