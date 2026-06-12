package com.pointofsale.view.sale;

import com.pointofsale.dao.ClienteDAO;
import com.pointofsale.dao.ProdutoDAO;
import com.pointofsale.model.Cliente;
import com.pointofsale.model.Produto;
import com.pointofsale.model.ProdutoVenda;
import com.pointofsale.service.ClienteService;
import com.pointofsale.service.VendaService;
import com.pointofsale.view.TelaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PainelNovaVenda extends JPanel {

    private final JTextField campoCpf;
    private final JTextField campoCodigo;
    private final JTextField campoQtd;
    private final JComboBox<String> comboPagamento;
    private final JLabel labelTotal;
    private final DefaultTableModel modelo;
    private final JTable tabela;
    private final TelaPrincipal principal;

    private final JButton botaoAdicionar;
    private final JButton botaoConcluir;
    private final JButton botaoIniciarVenda;
    private final JButton botaoCancelarVenda;

    private List<ProdutoVenda> carrinho = new ArrayList<>();
    private Cliente clienteAtual;

    public PainelNovaVenda(TelaPrincipal principal) {
        this.principal = principal;
        setLayout(null);

        JLabel labelCpf = new JLabel("CPF do Cliente:");
        labelCpf.setBounds(20, 15, 120, 25);
        add(labelCpf);
        campoCpf = new JTextField();
        campoCpf.setBounds(140, 15, 200, 25);
        add(campoCpf);

        botaoIniciarVenda = new JButton("Iniciar Venda");
        botaoIniciarVenda.setBounds(350, 15, 120, 25);
        add(botaoIniciarVenda);
        botaoIniciarVenda.addActionListener(e -> iniciarVenda());

        botaoCancelarVenda = new JButton("Cancelar");
        botaoCancelarVenda.setBounds(480, 15, 100, 25);
        add(botaoCancelarVenda);
        botaoCancelarVenda.addActionListener(e -> limparVenda());

        JLabel labelCodigo = new JLabel("Código de Barras:");
        labelCodigo.setBounds(20, 50, 120, 25);
        add(labelCodigo);
        campoCodigo = new JTextField();
        campoCodigo.setBounds(140, 50, 150, 25);
        add(campoCodigo);

        JLabel labelQtd = new JLabel("Qtd:");
        labelQtd.setBounds(300, 50, 40, 25);
        add(labelQtd);
        campoQtd = new JTextField("1");
        campoQtd.setBounds(335, 50, 50, 25);
        add(campoQtd);

        botaoAdicionar = new JButton("Adicionar item");
        botaoAdicionar.setBounds(400, 50, 150, 25);
        add(botaoAdicionar);
        botaoAdicionar.addActionListener(e -> adicionarItem());

        modelo = new DefaultTableModel(new Object[][]{}, new String[]{"Código", "Produto", "Qtd", "Subtotal"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 90, 630, 250);
        add(scroll);

        labelTotal = new JLabel("Total: R$ 0.00");
        labelTotal.setBounds(20, 350, 300, 30);
        labelTotal.setFont(labelTotal.getFont().deriveFont(18f));
        add(labelTotal);

        JLabel labelPagamento = new JLabel("Pagamento:");
        labelPagamento.setBounds(20, 400, 100, 25);
        add(labelPagamento);
        comboPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX"});
        comboPagamento.setBounds(120, 400, 200, 25);
        add(comboPagamento);

        botaoConcluir = new JButton("Concluir");
        botaoConcluir.setBounds(355, 400, 180, 40);
        add(botaoConcluir);
        botaoConcluir.addActionListener(e -> concluirVenda());

        JButton botaoVoltar = new JButton("← Menu");
        botaoVoltar.setBounds(530, 450, 120, 35);
        add(botaoVoltar);
        botaoVoltar.addActionListener(e -> {
            limparVenda();
            principal.mostrarTela("MENU");
        });

        setComponentesVendaAtivos(false);
    }

    private void setComponentesVendaAtivos(boolean ativo) {
        campoCodigo.setEnabled(ativo);
        campoQtd.setEnabled(ativo);
        botaoAdicionar.setEnabled(ativo);
        tabela.setEnabled(ativo);
        comboPagamento.setEnabled(ativo);
        botaoConcluir.setEnabled(ativo);

        campoCpf.setEnabled(!ativo);
        botaoIniciarVenda.setEnabled(!ativo);
        
        botaoCancelarVenda.setEnabled(ativo);
    }

    private void iniciarVenda() {
        try {
            String cpfTexto = campoCpf.getText().replaceAll("[^0-9]", "");
            if (cpfTexto.length() != 11) {
                throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
            }
            long cpf = Long.parseLong(cpfTexto);

            clienteAtual = ClienteDAO.buscarPorCpf(cpf);
            if (clienteAtual == null) {
                int resposta = JOptionPane.showConfirmDialog(this,
                        "Cliente não cadastrado. Deseja cadastrá-lo agora?",
                        "Novo Cliente", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    ClienteService.cadastrarNovoCliente(cpf);
                    clienteAtual = ClienteDAO.buscarPorCpf(cpf);
                } else {
                    return;
                }
            }
            
            if (clienteAtual != null) {
                setComponentesVendaAtivos(true);
                campoCodigo.requestFocus();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Use apenas números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarItem() {

        String codigo = campoCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um código de barras.");
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(campoQtd.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Produto produto = ProdutoDAO.buscarPorCodigo(codigo);
        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado: " + codigo, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProdutoVenda item = new ProdutoVenda(produto, quantidade);
        carrinho.add(item);
        modelo.addRow(new Object[]{produto.getCodigoBarras(), produto.getNome(), item.getQuantidade(), "R$ " + item.getSubtotal()});

        campoCodigo.setText("");
        campoQtd.setText("1");
        atualizarTotal();
    }

    private void concluirVenda() {
        try {
            if (clienteAtual == null) {
                throw new IllegalArgumentException("Nenhum cliente associado a esta venda.");
            }
            if (carrinho.isEmpty()) {
                throw new IllegalArgumentException("O carrinho está vazio.");
            }

            String pagamento = (String) comboPagamento.getSelectedItem();
            VendaService.processarVenda(clienteAtual, pagamento, carrinho);

            JOptionPane.showMessageDialog(this, "Venda concluída com sucesso!");
            limparVenda();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparVenda() {
        carrinho.clear();
        modelo.setRowCount(0);
        campoCpf.setText("");
        campoCodigo.setText("");
        campoQtd.setText("1");
        clienteAtual = null;
        atualizarTotal();
        setComponentesVendaAtivos(false);
        campoCpf.requestFocus();
    }

    private void atualizarTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ProdutoVenda item : carrinho) {
            total = total.add(item.getSubtotal());
        }
        labelTotal.setText("Total: R$ " + total);
    }
}
