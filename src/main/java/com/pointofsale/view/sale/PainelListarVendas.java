package com.pointofsale.view.sale;

import com.pointofsale.dao.VendaDAO;
import com.pointofsale.model.Venda;
import com.pointofsale.view.TelaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PainelListarVendas extends JPanel {

    private DefaultTableModel modelo;
    private JTable tabela;
    private List<Venda> vendas;
    private TelaPrincipal principal;

    // Molde de formatação da data: dia/mês/ano hora:minuto
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PainelListarVendas(TelaPrincipal principal) {
        this.principal = principal;
        setLayout(null);

        JLabel titulo = new JLabel("Histórico de Vendas");
        titulo.setBounds(20, 10, 200, 25);
        add(titulo);

        modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "CPF Cliente", "Data", "Total"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 45, 620, 380);
        add(scroll);

        JButton botaoDetalhe = new JButton("Ver Detalhes");
        botaoDetalhe.setBounds(20, 440, 140, 35);
        add(botaoDetalhe);
        botaoDetalhe.addActionListener(e -> abrirDetalhe());

        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(170, 440, 120, 35);
        add(botaoAtualizar);
        botaoAtualizar.addActionListener(e -> recarregar());

        JButton botaoVoltar = new JButton("← Menu");
        botaoVoltar.setBounds(520, 440, 120, 35);
        add(botaoVoltar);
        botaoVoltar.addActionListener(e -> principal.mostrarTela("MENU"));

        recarregar();
    }

    public void recarregar() {
        modelo.setRowCount(0);
        vendas = VendaDAO.listarVendas();

        for (Venda v : vendas) {
            long cpf = (v.getCliente() != null) ? v.getCliente().getCpf() : 0;
            modelo.addRow(new Object[]{
                    v.getIdVenda(),
                    cpf,
                    v.getDataHora().format(FORMATO_DATA),
                    "R$ " + v.getTotal()
            });
        }
    }

    private void abrirDetalhe() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda na tabela.");
            return;
        }
        Venda vendaSelecionada = vendas.get(linha);
        principal.mostrarDetalheVenda(vendaSelecionada);
    }
}