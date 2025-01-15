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
import com.vaadin.flow.data.value.ValueChangeMode;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.stream.Collectors;

@PermitAll
@Route("")
public class InventoryView extends VerticalLayout {

    private final ItemService itemService;
    private final Grid<Item> itemGrid = new Grid<>(Item.class, false);
    private final TextField nameFilter = new TextField("Filtrar por Nome");
    private final TextField itemNameField = new TextField("Nome do Item");
    private final TextField itemQuantityField = new TextField("Quantidade");
    private final Button addButton = new Button("Adicionar");
    private final Button clearButton = new Button("Limpar");

    private Item selectedItem = null;

    public InventoryView(ItemService itemService) {
        this.itemService = itemService;

        configureGrid();
        configureForm();
        configureFilter();

        HorizontalLayout filterLayout = new HorizontalLayout(nameFilter);
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, clearButton);
        buttonLayout.setAlignItems(Alignment.END);

        add(filterLayout, itemGrid, new HorizontalLayout(itemNameField, itemQuantityField), buttonLayout);

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

        itemGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedItem = event.getValue();
            if (selectedItem != null) {
                itemNameField.setValue(selectedItem.getName());
                itemQuantityField.setValue(String.valueOf(selectedItem.getQuantity()));
                addButton.setText("Atualizar");
            } else {
                clearForm();
            }
        });

        itemGrid.setItems(itemService.getAllItems());
    }

    private void configureForm() {
        addButton.addClickListener(click -> {
            if (selectedItem == null) {
                Item newItem = new Item();
                newItem.setName(itemNameField.getValue());
                newItem.setQuantity(Integer.parseInt(itemQuantityField.getValue()));
                itemService.saveItem(newItem);
                Notification.show("Item adicionado!");
            } else {
                selectedItem.setName(itemNameField.getValue());
                selectedItem.setQuantity(Integer.parseInt(itemQuantityField.getValue()));
                itemService.saveItem(selectedItem);
                Notification.show("Item atualizado!");
            }
            updateGrid();
            clearForm();
        });

        clearButton.addClickListener(click -> clearForm());
    }

    private void configureFilter() {
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        nameFilter.setValueChangeTimeout(300);

        nameFilter.addValueChangeListener(event -> {
            String filterText = event.getValue().toLowerCase();
            applyFilter(filterText);
        });
    }

    private void applyFilter(String filterText) {
        List<Item> filteredItems = itemService.getAllItems().stream()
                .filter(item -> matchesFilter(item.getName(), filterText))
                .collect(Collectors.toList());
        itemGrid.setItems(filteredItems);
    }

    private boolean matchesFilter(String fullName, String searchText) {
        String normalizedFullName = fullName.toLowerCase();
        String[] searchWords = searchText.toLowerCase().split(" ");
        return List.of(searchWords).stream().allMatch(word -> normalizedFullName.contains(word));
    }

    private void updateGrid() {
        itemGrid.setItems(itemService.getAllItems());
    }

    private void clearForm() {
        selectedItem = null;
        itemNameField.clear();
        itemQuantityField.clear();
        addButton.setText("Adicionar");
    }
}