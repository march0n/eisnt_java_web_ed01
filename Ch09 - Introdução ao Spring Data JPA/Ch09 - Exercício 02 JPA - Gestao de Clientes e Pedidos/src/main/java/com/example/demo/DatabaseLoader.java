package com.example.demo;

import com.example.demo.model.Cliente;
import com.example.demo.model.Pedido;
import com.example.demo.repository.ClienteRepositorio;
import com.example.demo.repository.PedidoRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(ClienteRepositorio clienteRepositorio, PedidoRepositorio pedidoRepositorio) {
        return args -> {
            Cliente cliente1 = new Cliente("João Silva", "joao.silva@example.com");
            Cliente cliente2 = new Cliente("Maria Oliveira", "maria.oliveira@example.com");

            clienteRepositorio.save(cliente1);
            clienteRepositorio.save(cliente2);

            pedidoRepositorio.save(new Pedido("Compra de teclado", cliente1));
            pedidoRepositorio.save(new Pedido("Compra de monitor", cliente2));
        };
    }
}
