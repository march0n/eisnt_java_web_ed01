package com.example.inventory.controller;

import com.example.inventory.model.Item;
import com.example.inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    
    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }
    
    // GET /items - permitido para qualquer utilizador (mesmo não autenticado)
    @GetMapping
    public List<Item> getAllItems(){
        return itemService.findAll();
    }
    
    // GET /items/{id} - permitido para qualquer utilizador
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id){
        try {
            Item item = itemService.findById(id);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST /items - apenas ADMIN pode criar itens
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item){
        Item created = itemService.createItem(item);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // PUT /items/{id} - apenas ADMIN pode atualizar *toda* a entidade
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item){
        try {
            Item updated = itemService.updateItem(id, item);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PATCH /items/{id}/quantidade - ADMIN e USER podem atualizar apenas a quantidade
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("/{id}/quantidade")
    public ResponseEntity<Item> updateQuantity(@PathVariable Long id, @RequestBody QuantidadeUpdateRequest request){
        try {
            Item updated = itemService.updateQuantity(id, request.getQuantidade());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /items/{id} - apenas ADMIN pode remover itens
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
    
    // Classe DTO para atualizar a quantidade
    public static class QuantidadeUpdateRequest {
        private int quantidade;

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }
}
