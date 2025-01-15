package com.example.inventory.views;

// Importa o componente H1 para criar um cabeçalho HTML
import com.vaadin.flow.component.html.H1;

// Importa o componente LoginForm, que fornece um formulário de autenticação pré-construído
import com.vaadin.flow.component.login.LoginForm;

// Importa o layout VerticalLayout para organizar os componentes verticalmente
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

// Importa as interfaces e classes relacionadas à navegação
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// Importa a anotação que permite acesso anónimo a esta vista
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Classe que representa a vista de login da aplicação.
 * Esta vista é responsável por fornecer um formulário de autenticação ao utilizador.
 */
@Route("login") // Define a rota da vista como "login"
@PageTitle("Login") // Define o título da página exibido no navegador
@AnonymousAllowed // Permite que esta vista seja acessada sem autenticação
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    // Instância do formulário de login, um componente Vaadin pré-construído
    private final LoginForm loginForm = new LoginForm();

    /**
     * Construtor da classe LoginView.
     * Configura o layout e adiciona os componentes necessários à vista de login.
     */
    public LoginView() {
        // Define uma classe CSS personalizada para esta vista
        addClassName("login-view");

        // Configura o layout para ocupar a tela inteira
        setSizeFull();

        // Centraliza os componentes no eixo horizontal
        setAlignItems(Alignment.CENTER);

        // Centraliza os componentes no eixo vertical
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Configura a ação do formulário de login para a rota "login"
        loginForm.setAction("login");

        // Oculta o botão de "Esqueci-me da palavra-passe"
        loginForm.setForgotPasswordButtonVisible(false);

        // Adiciona um cabeçalho e o formulário de login ao layout
        add(
            new H1("Login"), // Cabeçalho da página
            loginForm // Formulário de login
        );
    }

    /**
     * Método que é chamado antes de entrar nesta vista.
     * Permite verificar parâmetros ou estado antes de renderizar a vista.
     *
     * @param beforeEnterEvent Evento que contém informações sobre a navegação.
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Verifica se a URL contém o parâmetro "error"
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {

            // Define o estado de erro no formulário de login
            loginForm.setError(true);
        }
    }
}

/*
Notas adicionais:
1. @Route("login"):
   - Define o caminho no qual esta vista estará disponível (ex.: http://localhost:8080/login).
   - Permite organizar a navegação de forma declarativa.

2. LoginForm:
   - Este componente é pré-construído pela Vaadin e inclui campos para utilizador e palavra-passe.
   - "setAction(\"login\")" configura a submissão do formulário para a rota Spring Security por padrão.

3. beforeEnter(BeforeEnterEvent):
   - Verifica se a URL contém o parâmetro "error", que é geralmente adicionado pelo Spring Security quando uma tentativa de login falha.
   - Caso o parâmetro esteja presente, o formulário exibe automaticamente uma mensagem de erro.

4. Configuração de layout:
   - "setSizeFull()": Garante que o layout ocupa toda a área disponível.
   - "setAlignItems" e "setJustifyContentMode": Centralizam os componentes no ecrã, proporcionando uma experiência visual mais agradável.

5. Melhorias possíveis:
   - Adicionar internacionalização (i18n) para suportar múltiplos idiomas.
   - Incluir uma lógica de redirecionamento para utilizadores já autenticados.
   - Personalizar o estilo do formulário de login com CSS para alinhar com a identidade visual da aplicação.
*/
