package com.example.demo.service;

import com.example.demo.entity.Produto;

public class ProdutoService {

    public Produto criarProduto(Long id, String nome, Double preco) {
        return new Produto(id, nome, preco);
    }
}