package com.example.inventory.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // Cria os usuários em memória com senhas codificadas com BCrypt
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        var adminUser = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();
        var regularUser = User.builder()
                .username("user")
                .password(encoder.encode("user"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(adminUser, regularUser);
    }

    // Utiliza o BCryptPasswordEncoder para uma codificação segura
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração da cadeia de filtros de segurança, incluindo CORS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Ativa o CORS com a configuração definida no bean corsConfigurationSource()
            .cors(Customizer.withDefaults())
            // Desabilita o CSRF para facilitar testes com clientes REST
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Permite que requisições OPTIONS passem sem autenticação (necessário para CORS)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Permite que qualquer utilizador (mesmo não autenticado) aceda aos GETs em /items/**
                .requestMatchers(HttpMethod.GET, "/items/**").permitAll()
                // Qualquer outra requisição requer autenticação
                .anyRequest().authenticated()
            )
            // Ativa a autenticação via HTTP Basic
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Configuração global de CORS para permitir acesso do frontend (http://localhost:3000)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Autoriza a origem do frontend
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Permite os métodos HTTP utilizados
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Permite os headers que serão enviados
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Permite o envio de credenciais (cookies, cabeçalhos de autenticação)
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica a configuração para todas as rotas da aplicação
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
