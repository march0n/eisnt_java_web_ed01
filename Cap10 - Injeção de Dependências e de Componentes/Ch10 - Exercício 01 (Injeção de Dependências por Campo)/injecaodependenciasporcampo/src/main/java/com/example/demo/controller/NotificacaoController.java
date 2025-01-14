package com.example.demo.controller;

import com.example.demo.service.ServicoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacaoController {

    @Autowired
    private ServicoNotificacao servicoNotificacao;

    @GetMapping("/notificar")
    public String notificar(@RequestParam String destinatario, @RequestParam String mensagem) {
        servicoNotificacao.notificar(destinatario, mensagem);
        return "Notificação enviada para " + destinatario;
    }
}
