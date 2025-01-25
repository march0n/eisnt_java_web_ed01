package com.example.demo.service;

import com.example.demo.entity.Produto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoServiceTest {

    @Test
    public void testCriarProduto() {
        ProdutoService produtoService = new ProdutoService();

        Produto produto = produtoService.criarProduto(1L, "Produto Teste", 10.0);

        assertNotNull(produto);
        assertEquals(1L, produto.getId());
        assertEquals("Produto Teste", produto.getNome());
        assertEquals(10.0, produto.getPreco());
    }
}