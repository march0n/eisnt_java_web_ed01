package com.example.inventory.service;

import com.example.inventory.entity.Livro;
import com.example.inventory.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    public List<Livro> filterLivrosByReferencia(String referenciaFilter) {
        return livroRepository.findByReferenciaContainingIgnoreCase(referenciaFilter);
    }

    public Livro saveLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public void deleteLivro(Long id) {
        livroRepository.deleteById(id);
    }
}