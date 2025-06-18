package com.cotacao_moedas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // Indica que esta classe contém métodos de configuração de beans do Spring
public class WebClientConfig {

    private static final String AWESOME_API_BASE_URL = "https://economia.awesomeapi.com.br/";

    @Bean // Marca este método para que o Spring crie e gerencie um bean WebClient
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(AWESOME_API_BASE_URL) // Define a URL base para todas as requisições
                .build();
    }
}