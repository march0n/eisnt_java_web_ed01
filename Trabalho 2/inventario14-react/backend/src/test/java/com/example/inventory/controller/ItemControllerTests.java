package com.example.inventory.controller;

import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setup(){
        itemRepository.deleteAll();
        Item item = new Item();
        item.setReferencia("ref123");
        item.setNomeProduto("Produto A");
        item.setPreco(100.0);
        item.setDescricao("Descrição do produto A");
        item.setQuantidadeEmStock(10);
        item.setCategoria("Categoria 1");
        itemRepository.save(item);
    }
    
    @Test
    void testGetItemsWithoutAuth() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateItemWithAdmin() throws Exception {
        Item newItem = new Item();
        newItem.setReferencia("ref456");
        newItem.setNomeProduto("Produto B");
        newItem.setPreco(200.0);
        newItem.setDescricao("Descrição do produto B");
        newItem.setQuantidadeEmStock(20);
        newItem.setCategoria("Categoria 2");
        
        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.referencia", is("ref456")));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateItemWithAdmin() throws Exception {
        Item existing = itemRepository.findAll().get(0);
        existing.setNomeProduto("Produto A Atualizado");
        
        mockMvc.perform(put("/items/" + existing.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeProduto", is("Produto A Atualizado")));
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUpdateQuantityWithUser() throws Exception {
        Item existing = itemRepository.findAll().get(0);
        int novaQuantidade = 50;
        String payload = "{\"quantidade\": " + novaQuantidade + "}";
        
        mockMvc.perform(patch("/items/" + existing.getId() + "/quantidade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidadeEmStock", is(novaQuantidade)));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteItemWithAdmin() throws Exception {
        Item existing = itemRepository.findAll().get(0);
        
        mockMvc.perform(delete("/items/" + existing.getId()))
                .andExpect(status().isNoContent());
    }
}
