package com.pointofsale.view;

import com.pointofsale.model.Produto;
import com.pointofsale.view.product.PainelListarProdutos;
import com.pointofsale.view.product.PainelCadastroProduto;
import com.pointofsale.view.sale.PainelNovaVenda;
import com.pointofsale.view.sale.PainelListarVendas;
import com.pointofsale.view.sale.PainelDetalheVenda;
import com.pointofsale.model.Venda;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private CardLayout baralho;
    private JPanel painelCartas;

    private PainelListarProdutos painelListarProdutos;
    private PainelListarVendas painelListarVendas;
    private PainelCadastroProduto painelCadastroProduto;

    public TelaPrincipal() {
        setTitle("Sistema POS");
        setSize(680, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        baralho = new CardLayout();
        painelCartas = new JPanel(baralho);

        JPanel menu = criarMenu();
        painelCartas.add(menu, "MENU");

        painelListarProdutos = new PainelListarProdutos(this);
        painelCartas.add(painelListarProdutos, "LISTAR_PRODUTOS");

        painelCadastroProduto = new PainelCadastroProduto(this);
        painelCartas.add(painelCadastroProduto, "CADASTRO_PRODUTO");

        painelCartas.add(new PainelNovaVenda(this), "NOVA_VENDA");

        painelListarVendas = new PainelListarVendas(this);
        painelCartas.add(painelListarVendas, "LISTAR_VENDAS");

        add(painelCartas);

        mostrarTela("MENU");
    }

    public void mostrarTela(String nome) {
        if (nome.equals("CADASTRO_PRODUTO")) {
            painelCadastroProduto.limparCampos();
        }
        
        baralho.show(painelCartas, nome);

        if (nome.equals("LISTAR_PRODUTOS")) {
            painelListarProdutos.recarregar();
        } else if (nome.equals("LISTAR_VENDAS")) {
            painelListarVendas.recarregar();
        }
    }

    public void mostrarTelaCadastroProduto(Produto produto) {
        painelCadastroProduto.carregarDadosParaEdicao(produto);
        baralho.show(painelCartas, "CADASTRO_PRODUTO");
    }

    public void mostrarDetalheVenda(Venda venda) {
        PainelDetalheVenda painel = new PainelDetalheVenda(this, venda);
        painelCartas.add(painel, "DETALHE_VENDA");
        baralho.show(painelCartas, "DETALHE_VENDA");
    }

    private JPanel criarMenu() {
        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("Loja de Andinho e Pedinho");
        titulo.setBounds(230, 40, 250, 30);
        painel.add(titulo);

        JButton botaoProdutos = new JButton("Produtos");
        botaoProdutos.setBounds(240, 120, 200, 45);
        painel.add(botaoProdutos);
        botaoProdutos.addActionListener(e -> mostrarTela("LISTAR_PRODUTOS"));

        JButton botaoVenda = new JButton("Efetuar Venda");
        botaoVenda.setBounds(240, 185, 200, 45);
        painel.add(botaoVenda);
        botaoVenda.addActionListener(e -> mostrarTela("NOVA_VENDA"));

        JButton botaoHistorico = new JButton("Histórico de Vendas");
        botaoHistorico.setBounds(240, 250, 200, 45);
        painel.add(botaoHistorico);
        botaoHistorico.addActionListener(e -> mostrarTela("LISTAR_VENDAS"));

        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(240, 315, 200, 45);
        painel.add(botaoSair);
        botaoSair.addActionListener(e -> System.exit(0));

        return painel;
    }
}
