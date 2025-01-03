package com.example.adivinha.views.adivinhaweb;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Random;

@Route("") // Define a rota principal da aplicação
public class AdvinhaWebView extends VerticalLayout {

    private final int idadeSecreta; // Idade escolhida aleatoriamente
    private int tentativas = 0; // Contador de tentativas

    public AdvinhaWebView() {
        // Gera uma idade aleatória entre 1 e 100
        idadeSecreta = new Random().nextInt(100) + 1;

        // Campo para o utilizador inserir a sua tentativa
        TextField tentativaCampo = new TextField("Adivinha a idade:");
        tentativaCampo.setPlaceholder("Número entre 1 e 100");

        // Botão para verificar a tentativa
        Button verificarBotao = new Button("Verificar");

        // Botão para reiniciar o jogo
        Button reiniciarBotao = new Button("Reiniciar");
        reiniciarBotao.setVisible(false); // Oculto até que o jogo termine

        // Feedback ao utilizador
        Notification feedback = new Notification();
        feedback.setDuration(3000);

        // Lógica para verificar a tentativa
        verificarBotao.addClickListener(e -> {
            try {
                int tentativa = Integer.parseInt(tentativaCampo.getValue());
                tentativas++;

                if (tentativa < idadeSecreta) {
                    feedback.setText("Demasiado baixa! Tenta outra vez.");
                } else if (tentativa > idadeSecreta) {
                    feedback.setText("Demasiado alta! Tenta outra vez.");
                } else {
                    feedback.setText("Parabéns! Acertaste em " + tentativas + " tentativas!");
                    verificarBotao.setEnabled(false); // Desativar o botão
                    reiniciarBotao.setVisible(true); // Mostrar botão de reinício
                }

                feedback.open();
            } catch (NumberFormatException ex) {
                feedback.setText("Por favor, insere um número válido.");
                feedback.open();
            }
        });

        // Lógica para reiniciar o jogo
        reiniciarBotao.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.getPage().reload());
        });

        // Adicionar componentes ao layout
        add(tentativaCampo, verificarBotao, reiniciarBotao);
    }
}
