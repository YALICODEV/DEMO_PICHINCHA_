package com.banco.demogorest.config.security;

import com.banco.demogorest.config.security.filter.JwtFilter;
import com.banco.demogorest.config.security.handler.CustomAccessDeniedHandler;
import com.banco.demogorest.config.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {

    private final JwtFilter jwtFilter;
    private final SecurityContextRepository securityContextRepository;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return
            http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                exchanges -> {
                    exchanges.pathMatchers("/auth/login").permitAll();
                    exchanges.pathMatchers("/swagger-ui/**").permitAll();
                    exchanges.pathMatchers("/swagger-ui.html").permitAll();
                    exchanges.pathMatchers("/v3/api-docs/**").permitAll();
                    exchanges.pathMatchers("/webjars/**").permitAll();
                    exchanges.anyExchange().authenticated();
                }
                 )
                .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(customAccessDeniedHandler);
                    exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint);
                })
                .securityContextRepository(securityContextRepository)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .build();
    }

}
