package com.example.inventario.service;

import com.example.inventario.model.Livro;
import com.example.inventario.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro salvarOuAtualizar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void remover(Long id) {
        livroRepository.deleteById(id);
    }
}
