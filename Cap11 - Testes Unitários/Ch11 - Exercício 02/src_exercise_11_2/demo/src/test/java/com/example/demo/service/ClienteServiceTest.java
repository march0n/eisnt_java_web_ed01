package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarPorEmail() {
        Cliente cliente = new Cliente("João Silva", "joao.silva@example.com");
        when(clienteRepository.findByEmail("joao.silva@example.com")).thenReturn(cliente);

        Cliente resultado = clienteService.buscarPorEmail("joao.silva@example.com");

        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao.silva@example.com", resultado.getEmail());
    }
}
