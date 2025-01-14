package com.example.contactmanager.service;

import com.example.contactmanager.entity.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private final List<Contact> contacts = new ArrayList<>();

    public ContactService() {
        contacts.add(new Contact(1L, "João Silva", "joao.silva@example.com"));
        contacts.add(new Contact(2L, "Maria Oliveira", "maria.oliveira@example.com"));
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void saveContact(Contact contact) {
        if (contact.getId() == null) {
            contact.setId((long) (contacts.size() + 1));
        }
        contacts.add(contact);
    }

    public void deleteContact(Contact contact) {
        contacts.remove(contact);
    }
}
