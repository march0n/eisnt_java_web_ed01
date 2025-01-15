package com.example.inventory.views;

import com.example.inventory.entity.Livro;
import com.example.inventory.service.LivroService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa a interface de gestão de inventário.
 * Fornece funcionalidades para listar, filtrar, adicionar, atualizar e eliminar livros no inventário.
 */
@PermitAll
@Route("")
public class InventoryView extends VerticalLayout {

    // Serviço para gestão de livros no inventário
    private final LivroService livroService;

    // Componentes da interface

        // Componentes da interface

    // Grelha para exibir os livros no inventário. Cada linha representa um livro, e as colunas mostram atributos como referência, preço, descrição e quantidade.
    private final Grid<Livro> livroGrid = new Grid<>(Livro.class, false); 

    // Campo de texto para filtrar os livros por referência. Permite ao utilizador pesquisar itens na grelha.
    // Validação recomendada: garantir que apenas caracteres permitidos (ex.: letras e números) sejam introduzidos.
    // Como fazer: Utilizar `addValueChangeListener` e filtrar a entrada, removendo caracteres não permitidos com regex.
    private final TextField referenciaFilter = new TextField("Filtrar por Referência"); 

    // Campo de texto para introduzir a referência de um livro. Esta é uma propriedade obrigatória e, normalmente, deve ser única para cada livro.
    // Validação recomendada:
    // 1. Verificar unicidade: Antes de salvar, verificar se já existe outra entrada no sistema com a mesma referência.
    // 2. Garantir que não está vazio: Utilizar `isEmpty()` para alertar o utilizador se o campo estiver em branco.
    // 3. Garantir que segue um formato específico: Validar com regex (ex.: apenas alfanumérico).
    private final TextField referenciaField = new TextField("Referência"); 

    // Campo numérico para introduzir o preço do livro. Este campo aceita apenas números, com casas decimais para representar valores monetários.
    // Validação recomendada:
    // 1. Garantir que o valor é positivo: Utilizar um `ValueChangeListener` e verificar se o valor é maior que 0.
    // 2. Limitar o intervalo: Definir um mínimo e máximo com `setMin` e `setMax`.
    private final NumberField precoField = new NumberField("Preço"); 

    // Campo de texto para introduzir a descrição do livro. Permite ao utilizador adicionar detalhes sobre o item.
    // Validação recomendada:
    // 1. Limitar o número de caracteres: Definir `setMaxLength` para evitar textos muito longos.
    // 2. Garantir segurança: Sanitizar o texto para evitar injeções (ex.: remover scripts ou HTML).
    private final TextArea descricaoField = new TextArea("Descrição"); 

    // Campo numérico inteiro para introduzir a quantidade de livros em stock. Aceita apenas valores inteiros.
    // Validação recomendada:
    // 1. Garantir que o valor é maior ou igual a zero: Utilizar um `ValueChangeListener` e verificar se o valor é não negativo.
    // 2. Limitar o intervalo: Usar `setMin(0)` e definir um valor máximo, se necessário.
    private final IntegerField quantidadeField = new IntegerField("Quantidade"); 

    // Botão para adicionar ou atualizar livros no inventário. A ação depende do contexto: adiciona um novo livro ou atualiza um existente.
    // Validação recomendada antes de ativar:
    // 1. Garantir que todos os campos obrigatórios estão preenchidos: Verificar `isEmpty()` em todos os campos relevantes.
    // 2. Validar individualmente cada campo (ex.: unicidade da referência, valor positivo para preço e quantidade).
    // Como fazer: Desativar o botão por padrão e ativá-lo dinamicamente apenas quando todas as validações forem satisfeitas.
    private final Button addButton = new Button("Adicionar"); 

    // Botão para limpar o formulário. Ao ser clicado, todos os campos são reiniciados para os seus valores padrão.
    // Recomenda-se confirmar a ação com o utilizador para evitar a perda acidental de dados inseridos.
    // Como fazer: Adicionar um `Dialog` de confirmação antes de limpar os campos.
    private final Button clearButton = new Button("Limpar"); 



       // Livro atualmente selecionado para edição ou null se nenhum estiver selecionado.
    // Utilizado para distinguir entre operações de adição e atualização de livros.
    private Livro selectedLivro = null;

    /**
     * Construtor da classe InventoryView.
     * Configura os componentes da interface e organiza os layouts.
     *
     * @param livroService Serviço que fornece acesso às operações relacionadas a livros.
     */
    public InventoryView(LivroService livroService) {
        this.livroService = livroService;

        // Configuração inicial dos componentes e funcionalidades
        configureGrid(); // Configura a grelha para exibição dos livros
        configureForm(); // Configura o formulário de adição/edição
        configureFilter(); // Configura o filtro de pesquisa

        // Layouts para organizar os componentes na interface
        HorizontalLayout filterLayout = new HorizontalLayout(referenciaFilter); // Layout para o filtro
        HorizontalLayout formLayout = new HorizontalLayout(referenciaField, precoField, descricaoField, quantidadeField); // Layout para os campos do formulário
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, clearButton); // Layout para os botões
        buttonLayout.setAlignItems(Alignment.END); // Alinha os botões no final

        // Adiciona os layouts configurados à interface
        add(filterLayout, livroGrid, formLayout, buttonLayout);

        // Inicializa os dados da grelha
        updateGrid();
    }

    /**
     * Configura a grelha (Grid) para exibir os livros do inventário.
     */
    private void configureGrid() {
        // Adiciona colunas à grelha para mostrar os atributos do livro
        livroGrid.addColumn(Livro::getReferencia).setHeader("Referência"); // Coluna para a referência
        livroGrid.addColumn(Livro::getPreco).setHeader("Preço"); // Coluna para o preço
        livroGrid.addColumn(Livro::getDescricao).setHeader("Descrição"); // Coluna para a descrição
        livroGrid.addColumn(Livro::getQuantidade).setHeader("Quantidade"); // Coluna para a quantidade

        // Adiciona uma coluna com botões para eliminar livros
        livroGrid.addComponentColumn(livro -> {
            Button deleteButton = new Button("Eliminar", click -> {
                livroService.deleteLivro(livro.getId()); // Remove o livro do inventário
                updateGrid(); // Atualiza os dados exibidos na grelha
                Notification.show("Livro eliminado!"); // Mostra uma notificação ao utilizador
            });
            return deleteButton;
        });

        // Configura o comportamento de seleção de uma linha da grelha
        livroGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedLivro = event.getValue(); // Obtém o livro selecionado
            if (selectedLivro != null) {
                // Preenche o formulário com os dados do livro selecionado
                referenciaField.setValue(selectedLivro.getReferencia());
                precoField.setValue(selectedLivro.getPreco());
                descricaoField.setValue(selectedLivro.getDescricao());
                quantidadeField.setValue(selectedLivro.getQuantidade());
                addButton.setText("Atualizar"); // Altera o texto do botão para "Atualizar"
            } else {
                clearForm(); // Limpa o formulário se nenhuma linha estiver selecionada
            }
        });

        // Inicializa a grelha com os dados do serviço
        livroGrid.setItems(livroService.getAllLivros());
    }

    /**
     * Configura o formulário para adição/edição de livros.
     */
    private void configureForm() {
        addButton.addClickListener(click -> {
            if (selectedLivro == null) {
                // Adiciona um novo livro ao inventário
                Livro newLivro = new Livro();
                newLivro.setReferencia(referenciaField.getValue());
                newLivro.setPreco(precoField.getValue());
                newLivro.setDescricao(descricaoField.getValue());
                newLivro.setQuantidade(quantidadeField.getValue());
                livroService.saveLivro(newLivro); // Salva o novo livro
                Notification.show("Livro adicionado!"); // Notifica o utilizador
            } else {
                // Atualiza o livro selecionado
                selectedLivro.setReferencia(referenciaField.getValue());
                selectedLivro.setPreco(precoField.getValue());
                selectedLivro.setDescricao(descricaoField.getValue());
                selectedLivro.setQuantidade(quantidadeField.getValue());
                livroService.saveLivro(selectedLivro); // Salva as alterações no livro
                Notification.show("Livro atualizado!"); // Notifica o utilizador
            }
            updateGrid(); // Atualiza os dados da grelha
            clearForm(); // Limpa o formulário
        });

        clearButton.addClickListener(click -> clearForm()); // Limpa o formulário quando clicado
    }

    /**
     * Configura o filtro de pesquisa por referência.
     */
    private void configureFilter() {
        // Define o modo de alteração de valor para "lazy", atrasando a atualização do filtro para evitar chamadas excessivas
        referenciaFilter.setValueChangeMode(ValueChangeMode.LAZY);
        referenciaFilter.setValueChangeTimeout(300); // Define um tempo limite antes de aplicar o filtro

        // Aplica o filtro quando o valor do campo é alterado
        referenciaFilter.addValueChangeListener(event -> {
            String filterText = event.getValue().toLowerCase(); // Obtém o texto do filtro em minúsculas
            applyFilter(filterText); // Aplica o filtro
        });
    }

    /**
     * Aplica o filtro na grelha com base no texto fornecido.
     *
     * @param filterText Texto usado para filtrar os resultados.
     */
    private void applyFilter(String filterText) {
        List<Livro> filteredLivros = livroService.getAllLivros().stream()
                .filter(livro -> matchesFilter(livro.getReferencia(), filterText)) // Aplica o filtro à lista de livros
                .collect(Collectors.toList());
        livroGrid.setItems(filteredLivros); // Atualiza os itens exibidos na grelha
    }

    /**
     * Verifica se o texto da referência corresponde ao filtro.
     *
     * @param fullName Texto completo da referência.
     * @param searchText Texto do filtro.
     * @return Verdadeiro se todos os termos do filtro estiverem contidos na referência.
     */
    private boolean matchesFilter(String fullName, String searchText) {
        String normalizedFullName = fullName.toLowerCase();
        String[] searchWords = searchText.toLowerCase().split(" ");
        return List.of(searchWords).stream().allMatch(word -> normalizedFullName.contains(word));
    }

    /**
     * Atualiza os dados exibidos na grelha.
     */
    private void updateGrid() {
        livroGrid.setItems(livroService.getAllLivros()); // Obtém os livros do serviço e atualiza a grelha
    }

    /**
     * Limpa os campos do formulário e reseta o estado.
     */
    private void clearForm() {
        selectedLivro = null; // Reseta o livro selecionado
        referenciaField.clear(); // Limpa o campo de referência
        precoField.clear(); // Limpa o campo de preço
        descricaoField.clear(); // Limpa o campo de descrição
        quantidadeField.clear(); // Limpa o campo de quantidade
        addButton.setText("Adicionar"); // Reseta o texto do botão para "Adicionar"
    }
}




/* Exemplo de validações adicionais que podem ser implementadas:
 

// filtro de referência:
referenciaFilter.addValueChangeListener(event -> {
    String valorFiltrado = event.getValue().replaceAll("[^a-zA-Z0-9]", ""); // Apenas alfanuméricos
    referenciaFilter.setValue(valorFiltrado);
});


// unicidade de referência:
if (livroService.getAllLivros().stream().anyMatch(l -> l.getReferencia().equals(referenciaField.getValue()))) {
    Notification.show("A referência já existe!", 3000, Notification.Position.MIDDLE);
    return;
}


// limitações de valores numéricos:
precoField.addValueChangeListener(event -> {
    if (event.getValue() != null && event.getValue() <= 0) {
        Notification.show("O preço deve ser maior que zero!", 3000, Notification.Position.MIDDLE);
        precoField.clear();
    }
});



// limites de caracteres na descrição:
descricaoField.setMaxLength(255);
descricaoField.addValueChangeListener(event -> {
    if (descricaoField.getValue().length() > 255) {
        Notification.show("A descrição é demasiado longa!", 3000, Notification.Position.MIDDLE);
    }
});


// confirmação para limpar o formulário:
clearButton.addClickListener(click -> {
    Dialog confirmDialog = new Dialog();
    confirmDialog.add("Tem a certeza que deseja limpar os campos?");
    Button confirmButton = new Button("Sim", e -> {
        clearForm();
        confirmDialog.close();
    });
    Button cancelButton = new Button("Não", e -> confirmDialog.close());
    confirmDialog.add(new HorizontalLayout(confirmButton, cancelButton));
    confirmDialog.open();
});







*/