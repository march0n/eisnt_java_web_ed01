package com.example.inventory.repository;

import com.example.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByReferencia(String referencia);
}
