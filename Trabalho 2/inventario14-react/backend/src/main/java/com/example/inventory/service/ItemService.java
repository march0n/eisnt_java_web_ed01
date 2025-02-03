package com.example.inventory.service;

import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    
    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
    
    public List<Item> findAll(){
        return itemRepository.findAll();
    }
    
    public Item findById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }
    
    public Item createItem(Item item){
        return itemRepository.save(item);
    }
    
    public Item updateItem(Long id, Item updatedItem){
        return itemRepository.findById(id).map(item -> {
            item.setReferencia(updatedItem.getReferencia());
            item.setNomeProduto(updatedItem.getNomeProduto());
            item.setPreco(updatedItem.getPreco());
            item.setDescricao(updatedItem.getDescricao());
            item.setQuantidadeEmStock(updatedItem.getQuantidadeEmStock());
            item.setCategoria(updatedItem.getCategoria());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }
    
    public Item updateQuantity(Long id, int quantidade){
        return itemRepository.findById(id).map(item -> {
            item.setQuantidadeEmStock(quantidade);
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }
    
    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }
}
