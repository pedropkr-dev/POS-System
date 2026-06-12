package com.pointofsale.view.product;

import com.pointofsale.dao.ProdutoDAO;
import com.pointofsale.model.Produto;
import com.pointofsale.view.TelaPrincipal;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class PainelListarProdutos extends JPanel {

    private DefaultTableModel modelo;
    private JTable tabela;
    private JLabel labelImagem;
    private TelaPrincipal principal;

    private List<Produto> produtos;

    public PainelListarProdutos(TelaPrincipal principal) {
        this.principal = principal;
        setLayout(null);

        JLabel titulo = new JLabel("Produtos");
        titulo.setBounds(20, 10, 200, 25);
        add(titulo);

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
        scroll.setBounds(20, 45, 420, 380);
        add(scroll);

        JLabel tituloImagem = new JLabel("Imagem do produto:");
        tituloImagem.setBounds(460, 45, 180, 25);
        add(tituloImagem);

        labelImagem = new JLabel();
        labelImagem.setBounds(460, 75, 180, 180);
        labelImagem.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
        labelImagem.setText("(selecione um produto)");
        add(labelImagem);

        tabela.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                mostrarImagemSelecionada();
            }
        });

        JButton botaoNovo = new JButton("Novo Produto");
        botaoNovo.setBounds(20, 440, 120, 35);
        add(botaoNovo);
        botaoNovo.addActionListener(e -> principal.mostrarTela("CADASTRO_PRODUTO"));

        JButton botaoAlterar = new JButton("Alterar");
        botaoAlterar.setBounds(150, 440, 120, 35);
        add(botaoAlterar);
        botaoAlterar.addActionListener(e -> alterarProduto());

        JButton botaoVoltar = new JButton("← Menu");
        botaoVoltar.setBounds(520, 440, 120, 35);
        add(botaoVoltar);
        botaoVoltar.addActionListener(e -> principal.mostrarTela("MENU"));

        recarregar();
    }

    public void recarregar() {
        modelo.setRowCount(0);
        produtos = ProdutoDAO.listarProdutos();

        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                    p.getCodigoBarras(),
                    p.getNome(),
                    "R$ " + p.getValor()
            });
        }

        labelImagem.setIcon(null);
        labelImagem.setText("(selecione um produto)");
    }

    private void alterarProduto() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um produto na tabela para alterar.");
            return;
        }
        Produto produtoSelecionado = produtos.get(linhaSelecionada);
        principal.mostrarTelaCadastroProduto(produtoSelecionado);
    }

    private void mostrarImagemSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            return;
        }

        Produto produto = produtos.get(linha);
        String nomeImagem = produto.getLinkImagem();

        if (nomeImagem == null || nomeImagem.trim().isEmpty()) {
            labelImagem.setIcon(null);
            labelImagem.setText("(sem imagem)");
            return;
        }

        File arquivo = new File("dados/imagens/" + nomeImagem);
        if (!arquivo.exists()) {
            labelImagem.setIcon(null);
            labelImagem.setText("(imagem não encontrada)");
            return;
        }

        ImageIcon iconOriginal = new ImageIcon(arquivo.getAbsolutePath());
        Image imagemRedimensionada = iconOriginal.getImage()
                .getScaledInstance(180, 180, Image.SCALE_SMOOTH);

        labelImagem.setText("");
        labelImagem.setIcon(new ImageIcon(imagemRedimensionada));
    }
}
