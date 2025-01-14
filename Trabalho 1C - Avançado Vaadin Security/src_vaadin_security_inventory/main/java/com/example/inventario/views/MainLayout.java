package com.example.inventario.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;

public class MainLayout extends HorizontalLayout implements RouterLayout {

    public MainLayout() {
        H1 title = new H1("Inventário de Livros");
        Anchor logout = new Anchor("/logout", "Logout");
        logout.addClickListener(event -> VaadinSession.getCurrent().close());

        HorizontalLayout header = new HorizontalLayout(title, logout);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(header);
    }
}
