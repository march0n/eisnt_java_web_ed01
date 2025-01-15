package com.example.inventory.config;

import com.example.inventory.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/h2-console/**").permitAll();
            
        // Configuração do H2 Console
        http.csrf().disable();
        http.headers().frameOptions().disable();
        
        // Configuração do Vaadin
        super.configure(http);
        setLoginView(http, LoginView.class);
    }
}