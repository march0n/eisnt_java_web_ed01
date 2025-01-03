package com.example.adivinha.views.adivinhaweb;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Route("") // Define a rota principal da aplicação
public class AdvinhaWebView extends VerticalLayout {

    private final int idadeSecreta; // Idade escolhida aleatoriamente
    private int tentativas = 0; // Contador de tentativas

    private final JdbcTemplate jdbcTemplate; // Para interagir com a base de dados

    @Autowired
    public AdvinhaWebView(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // Configuração inicial: cria a tabela e insere um recorde alto
        inicializarBaseDeDados();

        // Gera uma idade aleatória entre 1 e 100
        idadeSecreta = new Random().nextInt(100) + 1;

        // Componentes
        TextField tentativaCampo = new TextField("Adivinha a idade:");
        tentativaCampo.setPlaceholder("Número entre 1 e 100");

        Button verificarBotao = new Button("Verificar");
        Button reiniciarBotao = new Button("Reiniciar");
        Button sairBotao = new Button("Sair");

        reiniciarBotao.setVisible(false);
        sairBotao.setVisible(false);

        Notification feedback = new Notification();
        feedback.setDuration(3000);

        // Lógica Central
        Runnable verificarLogica = () -> {
            try {
                int tentativa = Integer.parseInt(tentativaCampo.getValue());
                tentativas++;

                if (tentativa < idadeSecreta) {
                    feedback.setText("Demasiado baixa! Tenta outra vez.");
                } else if (tentativa > idadeSecreta) {
                    feedback.setText("Demasiado alta! Tenta outra vez.");
                } else {
                    // Verifica o recorde
                    int recordeAtual = obterRecorde();
                    if (tentativas < recordeAtual) {
                        atualizarRecorde(tentativas);
                        feedback.setText("Parabéns! Acertaste em " + tentativas + " tentativas! Novo recorde!");
                    } else {
                        feedback.setText("Parabéns! Acertaste em " + tentativas + " tentativas! "
                                + "O recorde atual é de " + recordeAtual + " tentativas.");
                    }

                    tentativaCampo.setEnabled(false);
                    verificarBotao.setEnabled(false);
                    reiniciarBotao.setVisible(true);
                    sairBotao.setVisible(true);
                }

                feedback.open();
            } catch (NumberFormatException ex) {
                feedback.setText("Por favor, insere um número válido.");
                feedback.open();
            }
        };

        // Evento do botão Verificar
        verificarBotao.addClickListener(e -> verificarLogica.run());

        // Evento Enter no campo TextField
        tentativaCampo.addKeyPressListener(Key.ENTER, e -> verificarLogica.run());

        // Reiniciar o jogo
        reiniciarBotao.addClickListener(e -> getUI().ifPresent(ui -> ui.getPage().reload()));

        // Sair do jogo
        sairBotao.addClickListener(e -> getUI().ifPresent(ui -> ui.getPage().executeJs("window.close();")));

        // Layout horizontal para Reiniciar e Sair
        HorizontalLayout botoesLayout = new HorizontalLayout(reiniciarBotao, sairBotao);

        // Adicionar componentes ao layout
        add(tentativaCampo, verificarBotao, botoesLayout);
    }

    // Inicializa a base de dados com um valor alto para o recorde
    private void inicializarBaseDeDados() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS recorde (tentativas_minimas INT)");
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM recorde", Integer.class);
        if (count == 0) {
            jdbcTemplate.update("INSERT INTO recorde (tentativas_minimas) VALUES (?)", 9999);
        }
    }

    // Lê o recorde atual
    private int obterRecorde() {
        return jdbcTemplate.queryForObject("SELECT tentativas_minimas FROM recorde", Integer.class);
    }

    // Atualiza o recorde
    private void atualizarRecorde(int novoRecorde) {
        jdbcTemplate.update("UPDATE recorde SET tentativas_minimas = ?", novoRecorde);
    }
}
