package com.example.livros;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir todas as rotas
                        .allowedOrigins("*") // Permitir requisições de qualquer origem
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Cabeçalhos permitidos
                        .allowCredentials(false); // Não permitir cookies ou autenticação
            }
        };
    }
}
