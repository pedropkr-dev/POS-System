package com.pointofsale.model;

public class Produto {
    private String nome;
    private int valor;
    private int id;
    private String linkImagem;

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
