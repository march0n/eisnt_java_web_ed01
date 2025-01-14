package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicoNotificacao {

    private ServicoEmail servicoEmail;

    @Autowired
    public void setServicoEmail(ServicoEmail servicoEmail) {
        this.servicoEmail = servicoEmail;
    }

    public void notificar(String destinatario, String mensagem) {
        servicoEmail.enviarEmail(destinatario, mensagem);
    }
}
