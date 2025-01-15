package com.example.inventory.service;

import com.example.inventory.entity.Livro;
import com.example.inventory.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciar operações relacionadas à entidade "Livro".
 * Este serviço atua como uma camada intermediária entre o controlador e o repositório,
 * encapsulando a lógica de negócio e a interação com o banco de dados.
 */
@Service // Indica que esta classe é um componente de serviço do Spring
public class LivroService {

    // Repositório responsável por acessar os dados da entidade Livro
    private final LivroRepository livroRepository;

    /**
     * Construtor para injetar o repositório de livros no serviço.
     *
     * @param livroRepository Instância do repositório de livros gerida pelo Spring.
     */
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    /**
     * Obtém todos os livros disponíveis no repositório.
     *
     * @return Uma lista contendo todos os livros.
     */
    public List<Livro> getAllLivros() {
        return livroRepository.findAll(); // Recupera todos os livros do banco de dados
    }

    /**
     * Filtra os livros com base na referência fornecida.
     * Utiliza uma busca parcial (case insensitive) para encontrar correspondências.
     *
     * @param referenciaFilter Texto de referência para filtrar os livros.
     * @return Uma lista de livros que contêm a referência fornecida.
     */
    public List<Livro> filterLivrosByReferencia(String referenciaFilter) {
        return livroRepository.findByReferenciaContainingIgnoreCase(referenciaFilter);
        // Busca livros onde a referência contém o texto fornecido, ignorando maiúsculas/minúsculas
    }

    /**
     * Salva ou atualiza um livro no banco de dados.
     * Se o livro já existir (baseado no ID), ele será atualizado; caso contrário, será adicionado.
     *
     * @param livro O objeto Livro a ser salvo.
     * @return O livro salvo ou atualizado.
     */
    public Livro saveLivro(Livro livro) {
        return livroRepository.save(livro); // Salva o livro no banco de dados
    }

    /**
     * Remove um livro do banco de dados com base no ID fornecido.
     *
     * @param id O identificador único do livro a ser removido.
     */
    public void deleteLivro(Long id) {
        livroRepository.deleteById(id); // Remove o livro pelo ID
    }
}

/*
Notas adicionais:
1. A anotação @Service:
   - Registra esta classe como um componente de serviço no contexto do Spring.
   - Permite que o serviço seja injetado em outras partes da aplicação usando @Autowired.

2. Métodos de negócio:
   - `getAllLivros`: Ideal para recuperar todos os livros do inventário.
   - `filterLivrosByReferencia`: Permite buscas flexíveis, sendo útil para filtros dinâmicos em interfaces de usuário.
   - `saveLivro`: Automatiza a lógica de adição/atualização de livros, dependendo da presença de um ID.
   - `deleteLivro`: Garante que os livros possam ser excluídos pelo identificador único.



3. Possíveis melhorias: validações "do lado do servidor" (backend) para garantir a integridade dos dados: note que já vimos como fazer validaçoes no frontend:

// Adicionar validação para garantir que dados inválidos não sejam salvos.
public Livro saveLivro(Livro livro) {
    // Validação dos campos obrigatórios
    if (livro.getReferencia() == null || livro.getReferencia().trim().isEmpty()) {
        throw new IllegalArgumentException("A referência do livro não pode estar vazia.");
    }
    if (livro.getPreco() == null || livro.getPreco() <= 0) {
        throw new IllegalArgumentException("O preço deve ser maior que zero.");
    }
    if (livro.getQuantidade() == null || livro.getQuantidade() < 0) {
        throw new IllegalArgumentException("A quantidade não pode ser negativa.");
    }

    // Validação adicional, por exemplo, evitar duplicados
    if (livroRepository.existsByReferencia(livro.getReferencia()) && (livro.getId() == null || !livroRepository.existsById(livro.getId()))) {
        throw new IllegalArgumentException("Já existe um livro com esta referência.");
    }

    // Salva o livro se todas as validações passarem
    return livroRepository.save(livro);
}


// Tratar exceções, como tentar excluir um livro inexistente.
public void deleteLivro(Long id) {
    if (!livroRepository.existsById(id)) {
        throw new IllegalArgumentException("Livro com o ID " + id + " não encontrado.");
    }

    livroRepository.deleteById(id);
}






   
*/
