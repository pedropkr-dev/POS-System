package com.pointofsale.model;

public class Produto {
    private String nome;
    private int valor;
    private int id;
    private String linkImagem;

    public Produto(String nome, int valor, int id, String linkImagem) {
        this.nome = nome;
        this.valor = valor;
        this.id = id;
        this.linkImagem = linkImagem;
    }

    public String getName() {
        return nome;
    }

    public int getValue() {
        return valor;
    }

    public int getId() {
        return id;
    }

    public String getLinkImage() {
        return linkImagem;
    }
}
