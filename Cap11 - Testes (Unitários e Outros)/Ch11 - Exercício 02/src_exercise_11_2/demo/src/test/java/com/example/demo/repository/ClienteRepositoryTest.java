package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testSalvarEObterCliente() {
        Cliente cliente = new Cliente("João Silva", "joao.silva@example.com");
        clienteRepository.save(cliente);

        Cliente encontrado = clienteRepository.findByEmail("joao.silva@example.com");

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNome()).isEqualTo("João Silva");
        assertThat(encontrado.getEmail()).isEqualTo("joao.silva@example.com");
    }
}
