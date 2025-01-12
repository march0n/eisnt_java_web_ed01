package com.example.livros;

import com.example.livros.model.Livro;
import com.example.livros.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner carregarDados(LivroRepository livroRepository) {
        return args -> {
            // Lista de livros iniciais
            livroRepository.save(new Livro("1984", "George Orwell"));
            livroRepository.save(new Livro("A Revolução dos Bichos", "George Orwell"));
            livroRepository.save(new Livro("Dom Quixote", "Miguel de Cervantes"));
            livroRepository.save(new Livro("Moby Dick", "Herman Melville"));
            livroRepository.save(new Livro("Guerra e Paz", "Lev Tolstói"));
            livroRepository.save(new Livro("Orgulho e Preconceito", "Jane Austen"));
            livroRepository.save(new Livro("Crime e Castigo", "Fiódor Dostoiévski"));
            livroRepository.save(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien"));
            livroRepository.save(new Livro("O Hobbit", "J.R.R. Tolkien"));
            livroRepository.save(new Livro("Cem Anos de Solidão", "Gabriel García Márquez"));
            livroRepository.save(new Livro("O Grande Gatsby", "F. Scott Fitzgerald"));
            livroRepository.save(new Livro("Ulisses", "James Joyce"));
            livroRepository.save(new Livro("A Metamorfose", "Franz Kafka"));
            livroRepository.save(new Livro("O Estrangeiro", "Albert Camus"));
            livroRepository.save(new Livro("A Divina Comédia", "Dante Alighieri"));
            livroRepository.save(new Livro("Hamlet", "William Shakespeare"));
            livroRepository.save(new Livro("O Retrato de Dorian Gray", "Oscar Wilde"));
            livroRepository.save(new Livro("Jane Eyre", "Charlotte Brontë"));
            livroRepository.save(new Livro("As Aventuras de Huckleberry Finn", "Mark Twain"));
            livroRepository.save(new Livro("Drácula", "Bram Stoker"));

            System.out.println("Dados iniciais carregados com sucesso!");
        };
    }
}
