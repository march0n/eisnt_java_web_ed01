package com.example.contactmanager.views;

import com.example.contactmanager.entity.Contact;
import com.example.contactmanager.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class ContactView extends VerticalLayout {

    private final ContactService contactService = new ContactService();

    private final Grid<Contact> contactGrid = new Grid<>(Contact.class, false);
    private final TextField nameField = new TextField("Nome");
    private final EmailField emailField = new EmailField("Email");
    private final Button saveButton = new Button("Salvar");
    private final Button clearButton = new Button("Limpar");

    private Contact selectedContact = null;

    public ContactView() {
        setSizeFull();

        // Configuração da grelha
        contactGrid.addColumn(Contact::getName).setHeader("Nome");
        contactGrid.addColumn(Contact::getEmail).setHeader("Email");
        contactGrid.addComponentColumn(contact -> {
            Button deleteButton = new Button("Eliminar", click -> {
                contactService.deleteContact(contact);
                updateGrid();
                Notification.show("Contacto eliminado!");
            });
            return deleteButton;
        });
        contactGrid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));

        // Configuração do formulário
        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, emailField);

        saveButton.addClickListener(event -> saveContact());
        clearButton.addClickListener(event -> clearForm());

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, clearButton);

        // Adicionar componentes ao layout principal
        add(contactGrid, formLayout, buttonsLayout);

        // Atualizar a grelha inicialmente
        updateGrid();
    }

    private void saveContact() {
        if (nameField.isEmpty() || emailField.isEmpty()) {
            Notification.show("Todos os campos são obrigatórios!", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (selectedContact == null) {
            Contact newContact = new Contact(null, nameField.getValue(), emailField.getValue());
            contactService.saveContact(newContact);
            Notification.show("Contacto adicionado!");
        } else {
            selectedContact.setName(nameField.getValue());
            selectedContact.setEmail(emailField.getValue());
            Notification.show("Contacto atualizado!");
        }

        updateGrid();
        clearForm();
    }

    private void clearForm() {
        selectedContact = null;
        nameField.clear();
        emailField.clear();
    }

    private void editContact(Contact contact) {
        if (contact != null) {
            selectedContact = contact;
            nameField.setValue(contact.getName());
            emailField.setValue(contact.getEmail());
        } else {
            clearForm();
        }
    }

    private void updateGrid() {
        contactGrid.setItems(contactService.getAllContacts());
    }
}
