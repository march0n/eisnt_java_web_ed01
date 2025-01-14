package com.example.inventario.views;

import com.example.inventario.model.Livro;
import com.example.inventario.service.LivroService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@RolesAllowed("USER")
@Route(value = "", layout = MainLayout.class)
public class InventoryView extends VerticalLayout {

    private final LivroService livroService;
    private final Grid<Livro> grid = new Grid<>(Livro.class);
    private final TextField filtro = new TextField("Filtrar por Referência ou Título");

    public InventoryView(LivroService livroService) {
        this.livroService = livroService;

        configurarGrid();
        configurarFiltro();

        add(filtro, grid);
        atualizarGrid();
    }

    private void configurarGrid() {
        grid.addColumn(Livro::getReferencia).setHeader("Referência");
        grid.addColumn(Livro::getTitulo).setHeader("Título");
        grid.addColumn(Livro::getDescricao).setHeader("Descrição");
        grid.addColumn(Livro::getPreco).setHeader("Preço (€)");
        grid.addColumn(Livro::getQuantidade).setHeader("Quantidade");
        grid.addComponentColumn(livro -> {
            Button remover = new Button("Remover", click -> {
                livroService.remover(livro.getId());
                atualizarGrid();
                Notification.show("Livro removido.");
            });
            return remover;
        });
    }

    private void configurarFiltro() {
        filtro.setPlaceholder("Digite uma referência ou título...");
        filtro.addValueChangeListener(event -> atualizarGrid());
    }

    private void atualizarGrid() {
        String filtroTexto = filtro.getValue();
        List<Livro> livros = filtroTexto == null || filtroTexto.isEmpty()
                ? livroService.listarTodos()
                : livroService.filtrarLivros(filtroTexto);
        grid.setItems(livros);
    }
}
