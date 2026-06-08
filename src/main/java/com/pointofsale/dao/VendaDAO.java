package com.pointofsale.dao;

import com.pointofsale.model.Cliente;
import com.pointofsale.model.Produto;
import com.pointofsale.model.ProdutoVenda;
import com.pointofsale.model.Venda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public static void salvarVenda(Venda venda) {
        File arquivo = new File("dados/venda.csv");

        try (FileWriter writer = new FileWriter(arquivo, true)) {

            String itensFormatados = "[";
            for (ProdutoVenda item : venda.getListaDeProdutos()) {
                itensFormatados += item.getProduto().getCodigoBarras() + "-" + item.getQuantidade() + "|";
            }
            itensFormatados += "]";

            String linhaCSV = venda.getIdVenda() + ";" +
                    venda.getCliente().getCPF() + ";" +
                    venda.getDataHora().toString() + ";" +
                    venda.getFormaPagamento() + ";" +
                    venda.getTotal().toString() + ";" +
                    itensFormatados + "\n";

            writer.write(linhaCSV);
        } catch (IOException e) {
            System.out.println("Erro. Não foi possível acessa a venda.");
        }

    }

    public static List<Venda> listarVendas() {
        List<Venda> historicoVendas = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(Paths.get("dados/venda.csv"));

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] fatias = linha.split(";");

                long idVenda = Long.parseLong(fatias[0]);
                long cpfCliente = Long.parseLong(fatias[1]);
                LocalDateTime dataHora = LocalDateTime.parse(fatias[2]);
                String formaPagamento = fatias[3];
                BigDecimal total = new BigDecimal(fatias[4]);
                String itensFormatados = fatias[5];

                Cliente cliente = ClienteDAO.buscarPorCpf(cpfCliente);

                List<ProdutoVenda> listaDeProdutos = new ArrayList<>();

                String itensLimpos = itensFormatados.replace("[", "").replace("]", "");

                if (!itensLimpos.isEmpty()) {
                    String[] arrayItens = itensLimpos.split("\\|");

                    for (String itemStr : arrayItens) {
                        if (itemStr.trim().isEmpty()) continue;

                        String[] dadosItem = itemStr.split("-");
                        String codigoBarras = dadosItem;
                        int quantidade = Integer.parseInt(dadosItem[5]);

                        Produto produtoOriginal = ProdutoDAO.buscarPorCodigo(codigoBarras);

                        // Com o produto e a quantidade em mãos, recriamos a linha da nota fiscal
                        ProdutoVenda produtoVenda = new ProdutoVenda(produtoOriginal, quantidade);
                        listaDeProdutos.add(produtoVenda);
                    }
                }

                Venda venda = new Venda(idVenda, cliente, formaPagamento, listaDeProdutos, dataHora, total);

                historicoVendas.add(venda);
            }

        } catch (IOException e) {
            System.out.println("Aviso: Nenhuma venda registrada ainda ou arquivo não encontrado.");
        }

        return historicoVendas;
    }
}
