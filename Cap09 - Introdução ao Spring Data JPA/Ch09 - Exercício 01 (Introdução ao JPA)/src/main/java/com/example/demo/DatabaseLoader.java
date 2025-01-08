package com.example.demo;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(ProdutoRepositorio produtoRepositorio) {
        return args -> {
            produtoRepositorio.save(new Produto("Teclado", 50.0));
            produtoRepositorio.save(new Produto("Rato", 30.0));
            produtoRepositorio.save(new Produto("Monitor", 200.0));
        };
    }
}
