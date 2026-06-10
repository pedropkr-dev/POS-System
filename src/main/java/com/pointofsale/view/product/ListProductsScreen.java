package com.pointofsale.view.product;

import com.pointofsale.dao.ProdutoDAO;
import com.pointofsale.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// Tela que LISTA os produtos numa tabela.
// Estende JFrame, então ela é uma janela.
public class ListProductsScreen extends JFrame {

    // Guardamos o modelo como atributo da classe porque vamos precisar
    // mexer nele em vários métodos (carregar, limpar, recarregar).
    private DefaultTableModel modelo;
    private JTable tabela;

    public ListProductsScreen() {

        // ---------- Configuração da janela ----------
        setTitle("Produtos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // fecha só ESTA janela, não o programa todo
        setLayout(null);

        // ---------- O modelo da tabela (onde os dados moram) ----------
        // O primeiro argumento {} são os dados iniciais (vazio).
        // O segundo são os NOMES das colunas.
        modelo = new DefaultTableModel(
                new Object[][]{},                                  // sem linhas no início
                new String[]{"Código de Barras", "Nome", "Valor"}  // cabeçalhos das colunas
        ) {
            // Isto impede o usuário de editar as células clicando nelas.
            // (a edição acontece pela tela de formulário, não direto na tabela)
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // ---------- A tabela (parte visual que exibe o modelo) ----------
        tabela = new JTable(modelo);

        // A tabela precisa ficar dentro de um JScrollPane para ter barra de
        // rolagem E para o cabeçalho das colunas aparecer corretamente.
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 20, 545, 280);
        add(scroll);

        // ---------- Botões ----------
        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(20, 320, 120, 35);
        add(botaoAtualizar);
        botaoAtualizar.addActionListener(e -> carregarProdutos());

        JButton botaoNovo = new JButton("Novo Produto");
        botaoNovo.setBounds(160, 320, 140, 35);
        add(botaoNovo);
        botaoNovo.addActionListener(e -> {
            System.out.println("Abrir tela de cadastro");
            // Futuro: new ProductFormScreen().setVisible(true);
        });

        // ---------- Carrega os produtos ao abrir a tela ----------
        carregarProdutos();
    }

    // Método que busca os produtos no DAO e enche a tabela.
    // Separado num método próprio para podermos chamá-lo de novo
    // (no botão Atualizar, ou depois de cadastrar um produto).
    private void carregarProdutos() {

        // 1. Limpa as linhas atuais (senão duplicaria a cada atualização).
        modelo.setRowCount(0);

        // 2. Pede a lista de produtos para o DAO do seu colega.
        List<Produto> produtos = ProdutoDAO.listarProdutos();

        // 3. Para cada produto, adiciona uma linha na tabela.
        for (Produto p : produtos) {
            // addRow recebe um array de Object — uma célula por coluna,
            // na mesma ordem dos cabeçalhos definidos lá em cima.
            modelo.addRow(new Object[]{
                    p.getCodigoBarras(),
                    p.getNome(),
                    "R$ " + p.getValor()
            });
        }
    }

    // main de teste para rodar esta tela isoladamente.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListProductsScreen().setVisible(true);
        });
    }
}