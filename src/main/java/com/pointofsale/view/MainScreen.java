package com.pointofsale.view;

import javax.swing.*;

public class MainScreen extends JFrame {


    public MainScreen() {

        setTitle("Sistema POS");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        add(painel);

        JLabel titulo = new JLabel("Frente de Loja");

        titulo.setBounds(160, 30, 200, 30);
        painel.add(titulo);


        JButton botaoProdutos = new JButton("Produtos");
        botaoProdutos.setBounds(100, 90, 200, 40);
        painel.add(botaoProdutos);

        botaoProdutos.addActionListener(e -> {
            System.out.println("Clicou em Produtos");

        });


        JButton botaoVenda = new JButton("Efetuar Venda");
        botaoVenda.setBounds(100, 145, 200, 40);
        painel.add(botaoVenda);

        botaoVenda.addActionListener(e -> {
            System.out.println("Clicou em Efetuar Venda");

        });


        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(100, 200, 200, 40);
        painel.add(botaoSair);

        botaoSair.addActionListener(e -> {
            System.exit(0);
        });
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainScreen().setVisible(true);
        });
    }
}