package com.pointofsale.model;

import java.math.BigDecimal;

public class ProdutoVenda {

    private Produto produto;
    private int quantidade;

    public ProdutoVenda(){
    }

    public ProdutoVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public BigDecimal getSubtotal(){
        if (produto == null || produto.getValor() == null) {
            return BigDecimal.ZERO;
        }
        return produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        if (produto != null) {
            return quantidade + "x " + produto.getNome() + " @ " + produto.getValor();
        }
        return "Item vazio";
    }
}