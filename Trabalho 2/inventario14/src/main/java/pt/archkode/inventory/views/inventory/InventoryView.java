package pt.archkode.inventory.views.inventory;

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
import pt.archkode.inventory.data.SampleInventory;
import pt.archkode.inventory.services.SampleInventoryService;
import pt.archkode.inventory.views.MainLayout;

@PageTitle("Inventory Manager")
@Route(value = "inventory-manager/:sampleInventoryID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed({ "USER", "ADMIN" })
public class InventoryView extends Div implements BeforeEnterObserver {

    private final String SAMPLEINVENTORY_ID = "sampleInventoryID";
    private final String SAMPLEINVENTORY_EDIT_ROUTE_TEMPLATE = "inventory-manager/%s/edit";

    private final Grid<SampleInventory> grid = new Grid<>(SampleInventory.class, false);

    private TextField ref;
    private TextField name;
    private TextField description;
    private TextField quantity;
    private TextField price;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<SampleInventory> binder;

    private SampleInventory sampleInventory;

    private final SampleInventoryService sampleInventoryService;

    public InventoryView(SampleInventoryService sampleInventoryService) {
        this.sampleInventoryService = sampleInventoryService;
        addClassNames("inventory-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("ref").setAutoWidth(true);
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
        grid.addColumn("quantity").setAutoWidth(true);
        grid.addColumn("price").setAutoWidth(true);
        grid.setItems(query -> sampleInventoryService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEINVENTORY_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(InventoryView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(SampleInventory.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(quantity).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("quantity");
        binder.forField(price).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("price");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.sampleInventory == null) {
                    this.sampleInventory = new SampleInventory();
                }
                binder.writeBean(this.sampleInventory);
                sampleInventoryService.update(this.sampleInventory);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(InventoryView.class);
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
        Optional<Long> sampleInventoryId = event.getRouteParameters().get(SAMPLEINVENTORY_ID).map(Long::parseLong);
        if (sampleInventoryId.isPresent()) {
            Optional<SampleInventory> sampleInventoryFromBackend = sampleInventoryService.get(sampleInventoryId.get());
            if (sampleInventoryFromBackend.isPresent()) {
                populateForm(sampleInventoryFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested sampleInventory was not found, ID = %s", sampleInventoryId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(InventoryView.class);
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
        boolean isAdmin = isUserAdmin();

        ref = new TextField("Reference");
        ref.setReadOnly(!isAdmin);

        name = new TextField("Name");
        name.setReadOnly(!isAdmin);

        description = new TextField("Description");
        description.setReadOnly(!isAdmin);

        quantity = new TextField("Quantity");

        price = new TextField("Price");
        price.setReadOnly(!isAdmin);

        formLayout.add(ref, name, description, quantity, price);

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

    private void populateForm(SampleInventory value) {
        this.sampleInventory = value;
        binder.readBean(this.sampleInventory);

    }

    private boolean isUserAdmin() {
        // Check if the current user has the "ROLE_ADMIN"
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
