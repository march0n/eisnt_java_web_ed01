package com.example.livros.service;

import com.example.livros.model.Livro;
import com.example.livros.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro procurarPorId(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public Livro salvarOuAtualizar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void remover(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }
}
