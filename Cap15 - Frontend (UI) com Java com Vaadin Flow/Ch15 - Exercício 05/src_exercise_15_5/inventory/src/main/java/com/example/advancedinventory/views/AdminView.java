package com.example.advancedinventory.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("admin")
public class AdminView extends VerticalLayout {

    public AdminView() {
        add(new Label("Área do Administrador"));

        Button manageCategories = new Button("Gerir Categorias", click -> {
            // Lógica para gerir categorias
        });

        Button viewReports = new Button("Ver Relatórios", click -> {
            // Lógica para relatórios avançados
        });

        add(manageCategories, viewReports);
    }
}
