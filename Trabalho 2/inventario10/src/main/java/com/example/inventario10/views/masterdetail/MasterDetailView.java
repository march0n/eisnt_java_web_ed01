package com.example.inventario10.views.masterdetail;

import com.example.inventario10.data.Inventario;
import com.example.inventario10.services.InventarioService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.PermitAll;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Master-Detail")
@Route("/:inventarioID?/:action?(edit)")
@Menu(order = 0, icon = LineAwesomeIconUrl.COLUMNS_SOLID)
@RouteAlias("")
@PermitAll
public class MasterDetailView extends Div implements BeforeEnterObserver {

    private final String INVENTARIO_ID = "inventarioID";
    private final String INVENTARIO_EDIT_ROUTE_TEMPLATE = "/%s/edit";

    private final Grid<Inventario> grid = new Grid<>(Inventario.class, false);

    private TextField referenciaProduto;
    private TextField nomeProduto;
    private TextField precoProduto;
    private TextField descricaoProduto;
    private TextField qtdStockProduto;
    private TextField categoriaProduto;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Inventario> binder;

    private Inventario inventario;

    private final InventarioService inventarioService;

    public MasterDetailView(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
        addClassNames("master-detail-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("referenciaProduto").setAutoWidth(true);
        grid.addColumn("nomeProduto").setAutoWidth(true);
        grid.addColumn("precoProduto").setAutoWidth(true);
        grid.addColumn("descricaoProduto").setAutoWidth(true);
        grid.addColumn("qtdStockProduto").setAutoWidth(true);
        grid.addColumn("categoriaProduto").setAutoWidth(true);
        grid.setItems(query -> inventarioService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(INVENTARIO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Inventario.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.inventario == null) {
                    this.inventario = new Inventario();
                }
                binder.writeBean(this.inventario);
                inventarioService.save(this.inventario);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(MasterDetailView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> inventarioId = event.getRouteParameters().get(INVENTARIO_ID).map(Long::parseLong);
        if (inventarioId.isPresent()) {
            Optional<Inventario> inventarioFromBackend = inventarioService.get(inventarioId.get());
            if (inventarioFromBackend.isPresent()) {
                populateForm(inventarioFromBackend.get());
            } else {
                Notification.show(String.format("The requested inventario was not found, ID = %s", inventarioId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        referenciaProduto = new TextField("Referencia Produto");
        nomeProduto = new TextField("Nome Produto");
        precoProduto = new TextField("Preco Produto");
        descricaoProduto = new TextField("Descricao Produto");
        qtdStockProduto = new TextField("Qtd Stock Produto");
        categoriaProduto = new TextField("Categoria Produto");
        formLayout.add(referenciaProduto, nomeProduto, precoProduto, descricaoProduto, qtdStockProduto,
                categoriaProduto);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Inventario value) {
        this.inventario = value;
        binder.readBean(this.inventario);

    }
}
