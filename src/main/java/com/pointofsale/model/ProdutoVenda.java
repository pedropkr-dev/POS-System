package com.pointofsale.model;

public class ProdutoVenda {
    private Produto produto;
    private short quantidade;
    private float valor;

    public ProdutoVenda(Produto produto, short quantidade, float valor) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }
}
