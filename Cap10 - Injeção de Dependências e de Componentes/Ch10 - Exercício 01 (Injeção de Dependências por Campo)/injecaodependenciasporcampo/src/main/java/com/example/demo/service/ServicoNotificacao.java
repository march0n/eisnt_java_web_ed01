package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicoNotificacao {

    @Autowired
    private ServicoEmail servicoEmail;

    public void notificar(String destinatario, String mensagem) {
        servicoEmail.enviarEmail(destinatario, mensagem);
    }
}
