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

@PermitAll
@Route("")
public class InventoryView extends VerticalLayout {

    private final LivroService livroService;
    private final Grid<Livro> livroGrid = new Grid<>(Livro.class, false);
    private final TextField referenciaFilter = new TextField("Filtrar por Referência");
    private final TextField referenciaField = new TextField("Referência");
    private final NumberField precoField = new NumberField("Preço");
    private final TextArea descricaoField = new TextArea("Descrição");
    private final IntegerField quantidadeField = new IntegerField("Quantidade");
    private final Button addButton = new Button("Adicionar");
    private final Button clearButton = new Button("Limpar");

    private Livro selectedLivro = null;

    public InventoryView(LivroService livroService) {
        this.livroService = livroService;

        configureGrid();
        configureForm();
        configureFilter();

        HorizontalLayout filterLayout = new HorizontalLayout(referenciaFilter);
        HorizontalLayout formLayout = new HorizontalLayout(referenciaField, precoField, descricaoField, quantidadeField);
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, clearButton);
        buttonLayout.setAlignItems(Alignment.END);

        add(filterLayout, livroGrid, formLayout, buttonLayout);

        updateGrid();
    }

    private void configureGrid() {
        livroGrid.addColumn(Livro::getReferencia).setHeader("Referência");
        livroGrid.addColumn(Livro::getPreco).setHeader("Preço");
        livroGrid.addColumn(Livro::getDescricao).setHeader("Descrição");
        livroGrid.addColumn(Livro::getQuantidade).setHeader("Quantidade");
        livroGrid.addComponentColumn(livro -> {
            Button deleteButton = new Button("Eliminar", click -> {
                livroService.deleteLivro(livro.getId());
                updateGrid();
                Notification.show("Livro eliminado!");
            });
            return deleteButton;
        });

        livroGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedLivro = event.getValue();
            if (selectedLivro != null) {
                referenciaField.setValue(selectedLivro.getReferencia());
                precoField.setValue(selectedLivro.getPreco());
                descricaoField.setValue(selectedLivro.getDescricao());
                quantidadeField.setValue(selectedLivro.getQuantidade());
                addButton.setText("Atualizar");
            } else {
                clearForm();
            }
        });

        livroGrid.setItems(livroService.getAllLivros());
    }

    private void configureForm() {
        addButton.addClickListener(click -> {
            if (selectedLivro == null) {
                Livro newLivro = new Livro();
                newLivro.setReferencia(referenciaField.getValue());
                newLivro.setPreco(precoField.getValue());
                newLivro.setDescricao(descricaoField.getValue());
                newLivro.setQuantidade(quantidadeField.getValue());
                livroService.saveLivro(newLivro);
                Notification.show("Livro adicionado!");
            } else {
                selectedLivro.setReferencia(referenciaField.getValue());
                selectedLivro.setPreco(precoField.getValue());
                selectedLivro.setDescricao(descricaoField.getValue());
                selectedLivro.setQuantidade(quantidadeField.getValue());
                livroService.saveLivro(selectedLivro);
                Notification.show("Livro atualizado!");
            }
            updateGrid();
            clearForm();
        });

        clearButton.addClickListener(click -> clearForm());
    }

    private void configureFilter() {
        referenciaFilter.setValueChangeMode(ValueChangeMode.LAZY);
        referenciaFilter.setValueChangeTimeout(300);

        referenciaFilter.addValueChangeListener(event -> {
            String filterText = event.getValue().toLowerCase();
            applyFilter(filterText);
        });
    }

    private void applyFilter(String filterText) {
        List<Livro> filteredLivros = livroService.getAllLivros().stream()
                .filter(livro -> matchesFilter(livro.getReferencia(), filterText))
                .collect(Collectors.toList());
        livroGrid.setItems(filteredLivros);
    }

    private boolean matchesFilter(String fullName, String searchText) {
        String normalizedFullName = fullName.toLowerCase();
        String[] searchWords = searchText.toLowerCase().split(" ");
        return List.of(searchWords).stream().allMatch(word -> normalizedFullName.contains(word));
    }

    private void updateGrid() {
        livroGrid.setItems(livroService.getAllLivros());
    }

    private void clearForm() {
        selectedLivro = null;
        referenciaField.clear();
        precoField.clear();
        descricaoField.clear();
        quantidadeField.clear();
        addButton.setText("Adicionar");
    }
}
