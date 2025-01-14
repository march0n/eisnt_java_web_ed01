package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class ServicoEmail {

    public void enviarEmail(String destinatario, String mensagem) {
        System.out.println("Enviando e-mail para " + destinatario + ": " + mensagem);
    }
}
