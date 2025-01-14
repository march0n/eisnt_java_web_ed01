package com.example.inventory.views;

import com.example.inventory.entity.Item;
import com.example.inventory.service.ItemService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class InventoryView extends VerticalLayout {

    private final ItemService itemService;
    private final Grid<Item> itemGrid = new Grid<>(Item.class, false);
    private final TextField nameFilter = new TextField("Filtrar por Nome");
    private final TextField itemNameField = new TextField("Nome do Item");
    private final TextField itemQuantityField = new TextField("Quantidade");
    private final Button addButton = new Button("Adicionar");
    private final Button clearButton = new Button("Limpar");

    public InventoryView(ItemService itemService) {
        this.itemService = itemService;

        configureGrid();
        configureForm();

        HorizontalLayout filterLayout = new HorizontalLayout(nameFilter);
        add(filterLayout, itemGrid, new HorizontalLayout(itemNameField, itemQuantityField, addButton, clearButton));

        updateGrid();
    }

    private void configureGrid() {
        itemGrid.addColumn(Item::getName).setHeader("Nome");
        itemGrid.addColumn(Item::getQuantity).setHeader("Quantidade");
        itemGrid.addComponentColumn(item -> {
            Button deleteButton = new Button("Eliminar", click -> {
                itemService.deleteItem(item.getId());
                updateGrid();
                Notification.show("Item eliminado!");
            });
            return deleteButton;
        });
        itemGrid.setItems(itemService.getAllItems());
    }

    private void configureForm() {
        addButton.addClickListener(click -> {
            String name = itemNameField.getValue();
            String quantityStr = itemQuantityField.getValue();

            if (name.isEmpty() || quantityStr.isEmpty()) {
                Notification.show("Todos os campos são obrigatórios!");
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            Item newItem = new Item(name, quantity, null);
            itemService.saveItem(newItem);

            updateGrid();
            clearForm();
            Notification.show("Item adicionado!");
        });

        clearButton.addClickListener(click -> clearForm());
    }

    private void clearForm() {
        itemNameField.clear();
        itemQuantityField.clear();
    }

    private void updateGrid() {
        String filter = nameFilter.getValue();
        List<Item> filteredItems = filter.isEmpty() ? itemService.getAllItems() : itemService.filterItemsByName(filter);
        itemGrid.setItems(filteredItems);
    }
}
