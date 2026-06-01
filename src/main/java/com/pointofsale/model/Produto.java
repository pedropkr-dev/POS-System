package com.pointofsale.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private String codigoBarras;
    private String nome;
    private BigDecimal valor;
    private int id;
    private String linkImagem;

    //Esse construtor vazio é para a Biblioteca Jackson conseguir ler do JSON.
    public Produto(){

    }

    public Produto(String codigoBarras, String nome, BigDecimal valor, int id, String linkImagem) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.valor = valor;
        this.id = id;
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
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }


    //Essa sobreescrita abaixo, ela é para que dois produtos com o mesmo código de barras, sejam considerados iguais.
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto outro = (Produto) o; //cast
        return Objects.equals(codigoBarras,outro.codigoBarras);
    }
    /*
    Essa sobreescrita abaixo tem que ser feita porque acima eu fiz o equals comparar pelo codigoBarras.
    Então o hashcode tbm precisa ser calculado a partir do codigobarras, Dessa maneira, dois produtos com o mesmo codigo de barras terão o mesmo hashcode.
    */
    @Override
    public int hashCode(){
        return Objects.hash(codigoBarras);
    }
    // E essa daqui é simplismente para definir o que aparece quando a gente tentar imprimir ou transformar o objeto em texto.
    @Override
    public String toString() {
        return nome + "(" + codigoBarras + ")";
    }
}
