package com.pointofsale.service;

import com.pointofsale.dao.ProdutoDAO;
import com.pointofsale.model.Produto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoService {

    public static void cadastrarProduto(String codigo, String nome, BigDecimal preco, String linkImagem) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O código de barras é obrigatório.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O nome do produto é obrigatório.");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Erro: O preço deve ser maior do que zero.");
        }

        if (ProdutoDAO.buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Erro: Código já cadastrado.");
        }

        Produto novoProduto = new Produto(codigo, nome, preco, linkImagem);
        ProdutoDAO.salvarProduto(novoProduto);

    }

    public static void atualizarProduto(String codigoOriginal, String nome, BigDecimal preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O nome do produto é obrigatório.");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Erro: O preço deve ser maior do que zero.");
        }

        List<Produto> todosProdutos = ProdutoDAO.listarProdutos();
        Produto produtoParaAtualizar = null;
        for (Produto p : todosProdutos) {
            if (p.getCodigoBarras().equals(codigoOriginal)) {
                produtoParaAtualizar = p;
                break;
            }
        }

        if (produtoParaAtualizar == null) {
            throw new IllegalArgumentException("Erro: Produto não encontrado para atualização.");
        }

        produtoParaAtualizar.setNome(nome);
        produtoParaAtualizar.setValor(preco);

        ProdutoDAO.atualizarProdutos(todosProdutos);
    }
}
