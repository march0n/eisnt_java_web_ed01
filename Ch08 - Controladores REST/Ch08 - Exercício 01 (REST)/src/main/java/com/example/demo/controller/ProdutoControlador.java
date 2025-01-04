package com.example.demo.controller;

import com.example.demo.model.Produto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoControlador {

    private List<Produto> produtos = new ArrayList<>();

    public ProdutoControlador() {
        produtos.add(new Produto(1L, "Computador", 1200.00));
        produtos.add(new Produto(2L, "Teclado", 50.00));
        produtos.add(new Produto(3L, "Rato", 25.00));
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtos;
    }

    @GetMapping("/{id}")
    public Produto obterProduto(@PathVariable Long id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        produtos.add(produto);
        return produto;
    }

    @GetMapping("/filtrar")
    public List<Produto> filtrarProdutos(@RequestParam String nome) {
        return produtos.stream()
                .filter(produto -> produto.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

}
