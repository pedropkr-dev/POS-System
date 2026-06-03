package com.pointofsale.model;

import java.math.BigDecimal;

public class ProdutoVenda {
    private String codigoBarras;
    private String nome;
    private int quantidade;
    private BigDecimal valorUnitario;

    public ProdutoVenda(){

    }

    public ProdutoVenda(Produto produto, int quantidade) {
        this.codigoBarras = produto.getCodigoBarras();
        this.nome = produto.getNome();
        this.quantidade = quantidade;
        this.valorUnitario = produto.getValor();
    }

    public BigDecimal getSubtotal(){
        if (valorUnitario == null) {
            return BigDecimal.ZERO;
        }
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return quantidade + "x " + nome + " @ " + valorUnitario;
    }
}
