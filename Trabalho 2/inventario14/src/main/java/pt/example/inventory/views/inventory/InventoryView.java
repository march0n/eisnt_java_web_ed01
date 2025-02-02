package pt.example.inventory.views.inventory;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import jakarta.annotation.security.RolesAllowed;
import pt.example.inventory.data.SampleInventory;
import pt.example.inventory.services.SampleInventoryService;
import pt.example.inventory.views.MainLayout;

@PageTitle("Inventory Manager")
@Route(value = "inventory-manager/:sampleInventoryID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed({ "USER", "ADMIN" })
public class InventoryView extends Div implements BeforeEnterObserver {

    // Constantes para os parâmetros de rota
    private static final String SAMPLEINVENTORY_ID = "sampleInventoryID";
    private static final String SAMPLEINVENTORY_EDIT_ROUTE_TEMPLATE = "inventory-manager/%s/edit";

    // Componentes da UI
    private final Grid<SampleInventory> grid = new Grid<>(SampleInventory.class, false);
    private TextField ref;
    private TextField name;
    private TextField description;
    private TextField quantity;
    private TextField price;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    // Binder para validação e binding
    private final BeanValidationBinder<SampleInventory> binder = new BeanValidationBinder<>(SampleInventory.class);

    private SampleInventory sampleInventory;
    private final SampleInventoryService sampleInventoryService;

    public InventoryView(SampleInventoryService sampleInventoryService) {
        this.sampleInventoryService = sampleInventoryService;
        addClassName("inventory-view");

        // Monta a UI principal
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        configureGrid();
        configureBinder();
        configureListeners();
    }

    /**
     * Configura o grid com as colunas e o data provider.
     */
    private void configureGrid() {
        grid.addColumn("ref").setAutoWidth(true);
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
        grid.addColumn("quantity").setAutoWidth(true);
        grid.addColumn("price").setAutoWidth(true);
        grid.setItems(query -> sampleInventoryService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEINVENTORY_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(InventoryView.class);
            }
        });
    }

    /**
     * Configura o binder, definindo conversores e realizando o binding dos campos.
     */
    private void configureBinder() {
        binder.forField(quantity)
                .withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("quantity");
        binder.forField(price)
                .withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("price");
        binder.bindInstanceFields(this);
    }

    /**
     * Configura os listeners dos botões.
     */
    private void configureListeners() {
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> handleSave());
    }

    /**
     * Trata o clique no botão Save.
     */
    private void handleSave() {
        if (areFieldsEmpty()) {
            showNotification("All fields must be filled before saving.", 3000, NotificationVariant.LUMO_ERROR, Position.MIDDLE);
            return;
        }
        try {
            if (this.sampleInventory == null) {
                this.sampleInventory = new SampleInventory();
            }
            binder.writeBean(this.sampleInventory);
            sampleInventoryService.update(this.sampleInventory);
            clearForm();
            refreshGrid();
            showNotification("Data updated", 3000, null, null);
            UI.getCurrent().navigate(InventoryView.class);
        } catch (ObjectOptimisticLockingFailureException exception) {
            showNotification("Error updating the data. Somebody else has updated the record while you were making changes.",
                    3000, NotificationVariant.LUMO_ERROR, Position.MIDDLE);
        } catch (ValidationException validationException) {
            showNotification("Failed to update the data. Check again that all values are valid",
                    3000, NotificationVariant.LUMO_ERROR, Position.MIDDLE);
        }
    }

    /**
     * Verifica se algum campo obrigatório está vazio.
     */
    private boolean areFieldsEmpty() {
        return ref.isEmpty() || name.isEmpty() || description.isEmpty() || quantity.isEmpty() || price.isEmpty();
    }

    /**
     * Exibe uma notificação customizada.
     */
    private void showNotification(String message, int duration, NotificationVariant variant, Position position) {
        Notification notification = Notification.show(message, duration, position != null ? position : Position.BOTTOM_START);
        if (variant != null) {
            notification.addThemeVariants(variant);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().get(SAMPLEINVENTORY_ID)
                .map(Long::parseLong)
                .ifPresentOrElse(id -> {
                    Optional<SampleInventory> sampleInventoryFromBackend = sampleInventoryService.get(id);
                    if (sampleInventoryFromBackend.isPresent()) {
                        populateForm(sampleInventoryFromBackend.get());
                    } else {
                        showNotification(String.format("The requested sampleInventory was not found, ID = %s", id),
                                3000, NotificationVariant.LUMO_ERROR, Position.BOTTOM_START);
                        refreshGrid();
                        event.forwardTo(InventoryView.class);
                    }
                }, () -> {
                    // Nenhum parâmetro de ID foi fornecido, nada a fazer
                });
    }

    /**
     * Cria a área de edição (formulário e botões).
     */
    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        boolean isAdmin = isUserAdmin();

        // Cria os campos de forma padronizada
        ref = createTextField("Reference", !isAdmin);
        name = createTextField("Name", !isAdmin);
        description = createTextField("Description", !isAdmin);
        // Mesmo que não se faça validação de leitura para os campos numéricos,
        // a conversão é feita pelo binder.
        quantity = createTextField("Quantity", false);
        price = createTextField("Price", !isAdmin);

        formLayout.add(ref, name, description, quantity, price);
        editorDiv.add(formLayout);

        createButtonLayout(editorLayoutDiv, isAdmin);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    /**
     * Cria um TextField com o rótulo e se o campo deve ser somente leitura.
     */
    private TextField createTextField(String label, boolean readOnly) {
        TextField field = new TextField(label);
        field.setReadOnly(readOnly);
        return field;
    }

    /**
     * Cria o layout dos botões (Save, Cancel e Delete se for admin).
     */
    private void createButtonLayout(Div editorLayoutDiv, boolean isAdmin) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");

        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);

        if (isAdmin) {
            Button delete = new Button("Delete");
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            delete.addClickListener(e -> handleDelete());
            buttonLayout.add(delete);
        }
        editorLayoutDiv.add(buttonLayout);
    }

    /**
     * Trata o clique no botão Delete.
     */
    private void handleDelete() {
        if (this.sampleInventory != null) {
            try {
                sampleInventoryService.delete(this.sampleInventory.getId());
                clearForm();
                refreshGrid();
                showNotification("Item deleted successfully.", 3000, null, null);
                UI.getCurrent().navigate(InventoryView.class);
            } catch (Exception ex) {
                showNotification("Failed to delete the item. Please try again.",
                        3000, NotificationVariant.LUMO_ERROR, Position.MIDDLE);
            }
        } else {
            showNotification("No item selected to delete.",
                    3000, NotificationVariant.LUMO_ERROR, Position.MIDDLE);
        }
    }

    /**
     * Cria o layout do grid.
     */
    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        wrapper.add(grid);
        splitLayout.addToPrimary(wrapper);
    }

    /**
     * Atualiza o grid e deseleciona o item atual.
     */
    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    /**
     * Limpa o formulário.
     */
    private void clearForm() {
        populateForm(null);
    }

    /**
     * Atualiza o binder com os dados do objeto ou limpa o formulário.
     */
    private void populateForm(SampleInventory inventory) {
        this.sampleInventory = inventory;
        binder.readBean(this.sampleInventory);
    }

    /**
     * Verifica se o utilizador atual possui o perfil de administrador.
     */
    private boolean isUserAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
