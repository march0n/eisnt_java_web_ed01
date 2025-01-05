package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoControlador {

    private final PedidoRepositorio pedidoRepositorio;

    public PedidoControlador(PedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepositorio.findAll();
    }

    @PostMapping
    public Pedido adicionarPedido(@RequestBody Pedido pedido) {
        return pedidoRepositorio.save(pedido);
    }
}
