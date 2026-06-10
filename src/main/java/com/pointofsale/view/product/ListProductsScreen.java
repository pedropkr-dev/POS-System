package com.pointofsale.view.product;

import com.pointofsale.dao.ProdutoDAO;
import com.pointofsale.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class ListProductsScreen extends JFrame {


    private DefaultTableModel modelo;
    private JTable tabela;

    public ListProductsScreen() {


        setTitle("Produtos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);


        modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código de Barras", "Nome", "Valor"}
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        tabela = new JTable(modelo);


        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 20, 545, 280);
        add(scroll);


        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(20, 320, 120, 35);
        add(botaoAtualizar);
        botaoAtualizar.addActionListener(e -> carregarProdutos());

        JButton botaoNovo = new JButton("Novo Produto");
        botaoNovo.setBounds(160, 320, 140, 35);
        add(botaoNovo);
        botaoNovo.addActionListener(e -> {
            System.out.println("Abrir tela de cadastro");

        });


        carregarProdutos();
    }


    private void carregarProdutos() {


        modelo.setRowCount(0);


        List<Produto> produtos = ProdutoDAO.listarProdutos();


        for (Produto p : produtos) {

            modelo.addRow(new Object[]{
                    p.getCodigoBarras(),
                    p.getNome(),
                    "R$ " + p.getValor()
            });
        }
    }

}