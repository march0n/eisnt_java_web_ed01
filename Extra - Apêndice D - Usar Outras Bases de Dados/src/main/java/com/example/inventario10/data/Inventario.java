package com.example.inventario10.data;

import jakarta.persistence.Entity;

@Entity
public class Inventario extends AbstractEntity {

    private String referenciaProduto;
    private String nomeProduto;
    private String precoProduto;
    private String descricaoProduto;
    private String qtdStockProduto;
    private String categoriaProduto;

    public String getReferenciaProduto() {
        return referenciaProduto;
    }
    public void setReferenciaProduto(String referenciaProduto) {
        this.referenciaProduto = referenciaProduto;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getPrecoProduto() {
        return precoProduto;
    }
    public void setPrecoProduto(String precoProduto) {
        this.precoProduto = precoProduto;
    }
    public String getDescricaoProduto() {
        return descricaoProduto;
    }
    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
    public String getQtdStockProduto() {
        return qtdStockProduto;
    }
    public void setQtdStockProduto(String qtdStockProduto) {
        this.qtdStockProduto = qtdStockProduto;
    }
    public String getCategoriaProduto() {
        return categoriaProduto;
    }
    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

}
