package com.pointofsale.view.sale;

import com.pointofsale.model.ProdutoVenda;
import com.pointofsale.model.Venda;
import com.pointofsale.view.TelaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class PainelDetalheVenda extends JPanel {

    public PainelDetalheVenda(TelaPrincipal principal, Venda venda) {
        setLayout(null);

        JLabel titulo = new JLabel("Detalhe da Venda #" + venda.getIdVenda());
        titulo.setBounds(20, 10, 300, 25);
        add(titulo);

        long cpf = (venda.getCliente() != null) ? venda.getCliente().getCpf() : 0;

        JLabel labelCpf = new JLabel("CPF do Cliente: " + cpf);
        labelCpf.setBounds(20, 45, 300, 25);
        add(labelCpf);

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        JLabel labelData = new JLabel("Data: " + venda.getDataHora().format(formatoData));
        labelData.setBounds(20, 70, 400, 25);
        add(labelData);

        JLabel labelPagamento = new JLabel("Pagamento: " + venda.getFormaPagamento());
        labelPagamento.setBounds(20, 95, 300, 25);
        add(labelPagamento);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Produto", "Qtd", "Subtotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 130, 620, 280);
        add(scroll);

        for (ProdutoVenda item : venda.getListaDeProdutos()) {
            String codigo = (item.getProduto() != null) ? item.getProduto().getCodigoBarras() : "?";
            String nome = (item.getProduto() != null) ? item.getProduto().getNome() : "(produto removido)";
            modelo.addRow(new Object[]{
                    codigo, nome, item.getQuantidade(), "R$ " + item.getSubtotal()
            });
        }

        JLabel labelTotal = new JLabel("Total: R$ " + venda.getTotal());
        labelTotal.setBounds(20, 420, 300, 30);
        labelTotal.setFont(labelTotal.getFont().deriveFont(16f));
        add(labelTotal);

        JButton botaoVoltar = new JButton("← Voltar");
        botaoVoltar.setBounds(520, 420, 120, 35);
        add(botaoVoltar);
        botaoVoltar.addActionListener(e -> principal.mostrarTela("LISTAR_VENDAS"));
    }
}