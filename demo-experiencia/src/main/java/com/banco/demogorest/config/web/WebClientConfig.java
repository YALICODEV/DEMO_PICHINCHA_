package com.banco.demogorest.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${support.api.url}")
    private String supportApiUrl;

    @Bean("goRestWebClient")
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://gorest.co.in/public/v2")
                .build();
    }

    @Bean("supportWebClient")
    public WebClient supportWebClient(WebClient.Builder builder) {
        return builder.baseUrl(supportApiUrl)
                .build();
    }

}
