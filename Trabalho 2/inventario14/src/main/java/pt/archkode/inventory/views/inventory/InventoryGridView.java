package pt.archkode.inventory.views.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pt.archkode.inventory.data.SampleInventory;
import pt.archkode.inventory.services.SampleInventoryService;
import pt.archkode.inventory.views.MainLayout;

@PageTitle("Inventory Listing")
@Route(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class InventoryGridView extends Div {

    private Grid<SampleInventory> grid;
    private Filters filters;
    private final SampleInventoryService sampleInventoryService;

    public InventoryGridView(SampleInventoryService sampleInventoryService) {
        this.sampleInventoryService = sampleInventoryService;
        setSizeFull();
        addClassNames("inventory-grid");

        filters = new Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    class Filters extends Div implements Specification<SampleInventory> {
        private final TextField ref = new TextField("Reference");
        private final TextField name = new TextField("Name");

        public Filters(Runnable onSearch) {
            setWidthFull();
            addClassNames("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            ref.setPlaceholder("Filter by reference");
            name.setPlaceholder("Filter by name");

            Button resetButton = new Button("Reset");
            resetButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetButton.addClickListener(click -> {
                ref.clear();
                name.clear();
                onSearch.run();
            });

            Button searchButton = new Button("Search");
            searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchButton.addClickListener(click -> onSearch.run());

            Div filterRow = new Div(ref, name, searchButton, resetButton);
            filterRow.addClassName("filter-row");
            filterRow.addClassNames(Display.FLEX, AlignItems.END, FlexDirection.ROW, Gap.LARGE);

            add(filterRow);
        }

        @Override
        public Predicate toPredicate(Root<SampleInventory> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!ref.isEmpty()) {
                String lowerCaseRef = ref.getValue().toLowerCase();
                Predicate refMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("ref")), lowerCaseRef + "%");
                predicates.add(refMatch);
            }

            if (!name.isEmpty()) {
                String lowerCaseName = name.getValue().toLowerCase();
                Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        lowerCaseName + "%");
                predicates.add(nameMatch);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(SampleInventory.class, false);
        grid.addColumn("ref").setAutoWidth(true);
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
        grid.addColumn("quantity").setAutoWidth(true);
        grid.addColumn("price").setAutoWidth(true);

        grid.setItems(query -> sampleInventoryService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

}
