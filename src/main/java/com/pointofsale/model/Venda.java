package com.pointofsale.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private long idVenda;
    private Cliente cliente;
    private String formaPagamento;
    private List<ProdutoVenda> listaDeProdutos;
    private LocalDateTime dataHora;
    private BigDecimal total;


    public Venda(long idVenda, Cliente cliente, String formaPagamento,List<ProdutoVenda> listaDeProdutos, LocalDateTime dataHora, BigDecimal total) {
        this.listaDeProdutos = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.listaDeProdutos = listaDeProdutos;
        this.dataHora = dataHora;
        this.total = total;
        this.idVenda = idVenda;
    }

    //gereciamento dos itens

    public void adicionarProduto(ProdutoVenda item){
        this.listaDeProdutos.add(item);
        recalcularTotal();
    }

    public void removerProduto(ProdutoVenda item){
        this.listaDeProdutos.remove(item);
        recalcularTotal();
    }

    public void recalcularTotal() {
        BigDecimal soma = BigDecimal.ZERO;
        for (ProdutoVenda item : listaDeProdutos){
            soma = soma.add(item.getSubtotal());
        }
        this.total = soma;
    }

    public long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(long idVenda) {
        this.idVenda = idVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ProdutoVenda> getListaDeProdutos() {
        return listaDeProdutos;
    }

    public void setListaDeProdutos(List<ProdutoVenda> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
