package com.example.inventario.repository;

import com.example.inventario.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByReferenciaContainingIgnoreCaseOrTituloContainingIgnoreCase(String referencia, String titulo);
}
