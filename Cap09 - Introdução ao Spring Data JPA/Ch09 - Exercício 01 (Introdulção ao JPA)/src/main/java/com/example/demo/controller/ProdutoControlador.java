package com.example.demo.controller;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoControlador {

    private final ProdutoRepositorio produtoRepositorio;

    public ProdutoControlador(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterProduto(@PathVariable Long id) {
        return produtoRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }    

    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return produtoRepositorio.save(produto);
    }
}
