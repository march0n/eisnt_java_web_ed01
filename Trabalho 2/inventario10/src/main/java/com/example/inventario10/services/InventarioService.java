package com.example.inventario10.services;

import com.example.inventario10.data.Inventario;
import com.example.inventario10.data.InventarioRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public Optional<Inventario> get(Long id) {
        return repository.findById(id);
    }

    public Inventario save(Inventario entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Inventario> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Inventario> list(Pageable pageable, Specification<Inventario> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
