package com.banco.demogorest.persistence.repository;

import com.banco.demogorest.persistence.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT id, name, email, password FROM USERS WHERE email = :email")
    Mono<User> findByEmail(String email);
}
