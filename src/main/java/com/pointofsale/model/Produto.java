package com.pointofsale.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Produto {
    private String codigoBarras;
    private String nome;
    private BigDecimal valor;
    private String linkImagem;

    public Produto(String codigoBarras, String nome, BigDecimal valor, String linkImagem) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.valor = valor;
        this.linkImagem = linkImagem;
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

    public BigDecimal getValor() {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }


    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

}
