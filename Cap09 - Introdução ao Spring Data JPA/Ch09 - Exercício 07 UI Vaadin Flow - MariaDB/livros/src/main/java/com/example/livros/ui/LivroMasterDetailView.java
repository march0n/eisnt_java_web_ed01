package com.example.livros.ui;

import com.example.livros.model.Livro;
import com.example.livros.service.LivroService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Optional;

@PageTitle("Livros - Master-Detail")
@Route("livros/:livroID/edit")
@RouteAlias("livros")
public class LivroMasterDetailView extends Div implements BeforeEnterObserver {

    private final String LIVRO_ID = "livroID";
    private final String LIVRO_EDIT_ROUTE_TEMPLATE = "/livros/%s/edit";

    private final Grid<Livro> grid = new Grid<>(Livro.class, false);

    private TextField titulo;
    private TextField autor;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private final Button delete = new Button("Remover");

    private final BeanValidationBinder<Livro> binder;

    private Livro livro;

    private final LivroService livroService;

    public LivroMasterDetailView(LivroService livroService) {
        this.livroService = livroService;

        addClassNames("livro-master-detail-view");

        // Layout principal
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        splitLayout.setSizeFull(); // Garantir que ocupa todo o ecrã
        splitLayout.setSplitterPosition(80); // 80% para o Grid, 20% para o formulário

        add(splitLayout);

        // Configurar Grid
        grid.addColumn("id").setHeader("ID").setAutoWidth(true);
        grid.addColumn("titulo").setHeader("Título").setAutoWidth(true);
        grid.addColumn("autor").setHeader("Autor").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        // Inicializar os itens no Grid
        grid.setItems(livroService.listarTodos());

        // Evento ao selecionar linha na Grid
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(LIVRO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(LivroMasterDetailView.class);
            }
        });

        // Configurar Form
        binder = new BeanValidationBinder<>(Livro.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.livro == null) {
                    this.livro = new Livro();
                }
                binder.writeBean(this.livro);
                livroService.salvarOuAtualizar(this.livro);
                clearForm();
                refreshGrid();
                Notification.show("Livro atualizado com sucesso!", 3000, Position.BOTTOM_START);
                UI.getCurrent().navigate(LivroMasterDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("Erro ao atualizar o livro. Verifique os dados.", 3000, Position.BOTTOM_START);
            }
        });

        delete.addClickListener(e -> {
            if (this.livro != null && this.livro.getId() != null) {
                try {
                    livroService.remover(this.livro.getId());
                    Notification.show("Livro removido com sucesso!", 3000, Position.BOTTOM_START);
                    clearForm();
                    refreshGrid();
                    UI.getCurrent().navigate(LivroMasterDetailView.class);
                } catch (RuntimeException ex) {
                    Notification.show("Erro ao remover o livro: " + ex.getMessage(), 3000, Position.BOTTOM_START);
                }
            } else {
                Notification.show("Selecione um livro para remover.", 3000, Position.BOTTOM_START);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> livroId = event.getRouteParameters().get(LIVRO_ID).map(Long::parseLong);

        if (livroId.isPresent()) {
            try {
                Livro livroFromBackend = livroService.procurarPorId(livroId.get());
                populateForm(livroFromBackend);
            } catch (RuntimeException e) {
                Notification.show(
                        String.format("O livro solicitado não foi encontrado, ID = %s", livroId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(LivroMasterDetailView.class);
            }
        }
        setSizeFull(); // Garantir que o SplitLayout ocupa todo o espaço do Div principal
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        VerticalLayout editorDiv = new VerticalLayout();
        editorDiv.setClassName("editor");
        editorDiv.setSizeFull(); // Forçar que o editor ocupa o espaço definido no SplitLayout
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        titulo = new TextField("Título");
        autor = new TextField("Autor");
        formLayout.add(titulo, autor);

        editorDiv.add(formLayout);
        createButtonLayout(editorDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(VerticalLayout editorDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");

        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttonLayout.add(save, cancel, delete);
        editorDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");

        wrapper.setSizeFull(); // Garantir que o wrapper ocupa todo o espaço
        wrapper.getStyle().set("overflow", "auto"); // Garantir que o conteúdo pode rolar se necessário

        grid.setHeight("100%"); // Garantir que o Grid ocupa todo o espaço vertical dentro do wrapper
        grid.setWidth("100%"); // Garantir que o Grid ocupa toda a largura do wrapper
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        wrapper.add(grid);
        splitLayout.addToPrimary(wrapper);
    }

    private void refreshGrid() {
        grid.setItems(livroService.listarTodos());
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Livro value) {
        this.livro = value;
        binder.readBean(this.livro);
    }
}
