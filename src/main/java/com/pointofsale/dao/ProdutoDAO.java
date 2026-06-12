package com.pointofsale.dao;

import com.pointofsale.model.Produto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public static void salvarProduto(Produto produto) {
        File arquivo = new File("dados/produtos.csv");

        File pasta = new File("dados");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (FileWriter writer = new FileWriter(arquivo, true)) {

            String linhaCSV = produto.getCodigoBarras() + ";" +
                    produto.getNome() + ";" +
                    produto.getValor() + ";" +
                    produto.getLinkImagem() + "\n";

            writer.write(linhaCSV);

        } catch (IOException e) {
            System.out.println("Erro. Não foi possível acessar o produto.");
            e.printStackTrace();
        }
    }

    public static List<Produto> listarProdutos() {

        List<Produto> catalogo = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(Paths.get("dados/produtos.csv"));

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] fatias = linha.split(";", -1);

                String codigo = fatias[0];
                String nome = fatias[1];
                BigDecimal preco = new BigDecimal(fatias[2]);
                String linkImagem = (fatias.length > 3) ? fatias[3] : "";

                Produto p = new Produto(codigo, nome, preco, linkImagem);

                catalogo.add(p);
            }

        } catch (IOException e) {
            System.out.println("Erro. Nenhum produto encontrado.");
        }

        return catalogo;
    }

    public static Produto buscarPorCodigo(String codigoBuscado) {
        List<Produto> catalogo = listarProdutos();

        for (Produto p : catalogo) {
            if (p.getCodigoBarras().equals(codigoBuscado)) {
                return p;
            }
        }

        return null;
    }

    public static void atualizarProdutos(List<Produto> produtos) {
        File arquivo = new File("dados/produtos.csv");
        try (FileWriter writer = new FileWriter(arquivo, false)) {
            for (Produto p : produtos) {
                String linhaCSV = p.getCodigoBarras() + ";" +
                                  p.getNome() + ";" +
                                  p.getValor() + ";" +
                                  p.getLinkImagem() + "\n";
                writer.write(linhaCSV);
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de produtos.");
            e.printStackTrace();
        }
    }
}
