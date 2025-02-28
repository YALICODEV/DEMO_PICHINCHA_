package com.banco.demogorest.service.implementation;

import com.banco.demogorest.persistence.repository.UserReactiveRepository;
import com.banco.demogorest.presentation.dto.request.LoginRequest;
import com.banco.demogorest.presentation.dto.response.TokenResponse;
import com.banco.demogorest.service.exception.BadCredentialException;
import com.banco.demogorest.service.interfaces.UserService;
import com.banco.demogorest.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReactiveRepository userReactiveRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public Mono<TokenResponse> login(LoginRequest dto) {
        return userReactiveRepository.findByEmail(dto.email())
                .filter(user -> passwordEncoder.matches(dto.password(), user.getPassword()))
                .map(user -> new TokenResponse(jwtProvider.generateToken(user)))
                .switchIfEmpty(Mono.error(new BadCredentialException("Invalid credentials")));
    }
}
