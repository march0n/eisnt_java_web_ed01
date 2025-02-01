package com.example.inventory.model;

import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo "referência" único que identifica o item (pode ser, por exemplo, um código de produto)
    @Column(nullable = false, unique = true)
    private String referencia;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = false)
    private double preco;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false)
    private int quantidadeEmStock;

    @Column
    private String categoria;

    // Construtores
    public Item() {
    }

    public Item(String referencia, String nomeProduto, double preco, String descricao, int quantidadeEmStock, String categoria) {
        this.referencia = referencia;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidadeEmStock = quantidadeEmStock;
        this.categoria = categoria;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeEmStock() {
        return quantidadeEmStock;
    }

    public void setQuantidadeEmStock(int quantidadeEmStock) {
        this.quantidadeEmStock = quantidadeEmStock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
