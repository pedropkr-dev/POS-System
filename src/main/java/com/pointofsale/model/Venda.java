package com.pointofsale.model;

import java.util.ArrayList;

public class Venda {
    private Cliente cliente;
    private String formaPagamento;
    private ArrayList<ProdutoVenda> listaDeProdutos;

    public Venda(Cliente cliente, String formaPagamento, ArrayList<ProdutoVenda> listaDeProdutos) {
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.listaDeProdutos = new ArrayList<>();
    }
}
