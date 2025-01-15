package com.example.inventory.repository;

import com.example.inventory.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório da entidade \"Livro\" que fornece acesso ao banco de dados.
 * Este repositório herda os métodos padrão de JpaRepository e adiciona métodos personalizados para consultas específicas.
 */
@Repository // Indica que esta interface é um componente gerido pelo Spring para persistência de dados
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Encontra livros com base em uma parte da referência fornecida, ignorando maiúsculas/minúsculas.
     * Este método utiliza a convenção de nomenclatura do Spring Data JPA para gerar automaticamente a consulta necessária.
     *
     * @param referencia Texto (ou parte) da referência do livro a ser buscado.
     * @return Uma lista de livros que contêm o texto da referência fornecida.
     */
    List<Livro> findByReferenciaContainingIgnoreCase(String referencia);
}

/*
Notas adicionais:

1. Extensão JpaRepository:
   - Ao estender JpaRepository, o repositório herda métodos padrão como save, findById, deleteById, findAll, entre outros.
   - Isso reduz a quantidade de código necessário para operações básicas de CRUD (Create, Read, Update, Delete).

2. Consulta personalizada:
   - O método `findByReferenciaContainingIgnoreCase` é criado automaticamente com base na convenção de nomenclatura.
   - O Spring Data JPA traduz o nome do método em uma consulta JPQL, equivalente a:
     `SELECT l FROM Livro l WHERE LOWER(l.referencia) LIKE LOWER(CONCAT('%', :referencia, '%'))`.
   - Isso permite buscas parciais na coluna `referencia` sem a necessidade de escrever consultas manuais.

3. Anotação @Repository:
   - Registra o repositório no contexto do Spring, permitindo sua injeção em serviços ou controladores com @Autowired ou por construtor.
   - Simplifica a gestão de exceções relacionadas ao banco de dados, convertendo-as em exceções do Spring (ex.: DataAccessException).


*/