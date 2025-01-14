package com.example.inventario.controller;

import com.example.inventario.model.Livro;
import com.example.inventario.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @PostMapping
    public Livro salvar(@RequestBody Livro livro) {
        return livroService.salvarOuAtualizar(livro);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        livroService.remover(id);
    }
}
