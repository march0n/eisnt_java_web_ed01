package com.example.inventory.config;

import com.example.inventory.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Configuração de segurança da aplicação.
 * Extende a classe VaadinWebSecurity para integrar o Spring Security com Vaadin.
 */
@EnableWebSecurity // Habilita a segurança baseada em web no Spring
@Configuration // Marca esta classe como uma configuração Spring
public class SecurityConfig extends VaadinWebSecurity {

    /**
     * Configura as políticas de segurança HTTP para a aplicação.
     *
     * @param http Instância do HttpSecurity fornecida pelo Spring Security.
     * @throws Exception Caso ocorra algum erro durante a configuração.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Permite o acesso irrestrito ao H2 Console (usado para depuração e testes)
        http.authorizeHttpRequests()
            .requestMatchers("/h2-console/**").permitAll(); // Qualquer usuário pode acessar o H2 Console
        
        // Configurações específicas para o H2 Console
        http.csrf().disable(); // Desabilita a proteção contra CSRF para permitir acesso ao H2 Console
        http.headers().frameOptions().disable(); // Permite a exibição de frames, necessária para o H2 Console
        
        // Integração com a configuração de segurança padrão do Vaadin
        super.configure(http);

        // Define a LoginView como a página de login personalizada da aplicação
        setLoginView(http, LoginView.class);
    }
}

/*
Notas adicionais:

1. Integração com Spring Security e Vaadin:
   - A classe SecurityConfig extende VaadinWebSecurity, que fornece integração pronta entre o Spring Security e o Vaadin.
   - Isso inclui a proteção automática de rotas e a configuração de uma página de login.

2. Configuração do H2 Console:
   - `csrf().disable()`: Necessário para evitar bloqueios durante o envio de formulários no H2 Console.
   - `frameOptions().disable()`: Permite que o H2 Console seja carregado em um frame, o que é bloqueado por padrão.
   - Use esta configuração somente em ambientes de desenvolvimento/teste, pois desabilitar CSRF e frameOptions expõe riscos de segurança.

3. Método setLoginView:
   - Especifica que a página LoginView será usada para autenticação de usuários.
   - A página de login será exibida automaticamente quando um usuário não autenticado tentar acessar rotas protegidas.

4. Personalização adicional:
   - Pode definir diferentes regras de acesso para rotas específicas usando `http.authorizeHttpRequests()`.
   - Exemplo:
     ```java
     http.authorizeHttpRequests()
         .requestMatchers("/admin/**").hasRole("ADMIN")
         .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
         .anyRequest().authenticated();
     ```

5. Melhorias recomendadas:
   - Adicionar suporte a HTTPS para proteger a transmissão de dados sensíveis.
   - Implementar configurações adicionais, como políticas de segurança de conteúdo (CSP).
   - Configurar logout personalizado e redirecionamento pós-login com:
     ```java
     http.logout().logoutSuccessUrl("/");
     ```
*/
