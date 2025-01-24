package com.example.inventario10.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventarioRepository extends JpaRepository<Inventario, Long>, JpaSpecificationExecutor<Inventario> {

}
