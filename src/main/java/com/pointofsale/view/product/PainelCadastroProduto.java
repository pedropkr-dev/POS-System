package com.pointofsale.view.product;

import com.pointofsale.model.Produto;
import com.pointofsale.service.ProdutoService;
import com.pointofsale.view.TelaPrincipal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PainelCadastroProduto extends JPanel {

    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoValor;
    private JTextField campoImagem;
    private JButton botaoEscolher;
    private TelaPrincipal principal;

    private File arquivoImagemEscolhido;
    private Produto produtoEmEdicao;

    public PainelCadastroProduto(TelaPrincipal principal) {
        this.principal = principal;

        setLayout(null);

        JLabel titulo = new JLabel("Cadastrar Produto");
        titulo.setBounds(30, 10, 200, 25);
        add(titulo);

        JLabel labelCodigo = new JLabel("Código de Barras:");
        labelCodigo.setBounds(30, 50, 150, 25);
        add(labelCodigo);
        campoCodigo = new JTextField();
        campoCodigo.setBounds(180, 50, 220, 25);
        add(campoCodigo);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(30, 90, 150, 25);
        add(labelNome);
        campoNome = new JTextField();
        campoNome.setBounds(180, 90, 220, 25);
        add(campoNome);

        JLabel labelValor = new JLabel("Valor (ex: 8.99):");
        labelValor.setBounds(30, 130, 150, 25);
        add(labelValor);
        campoValor = new JTextField();
        campoValor.setBounds(180, 130, 220, 25);
        add(campoValor);

        JLabel labelImagem = new JLabel("Imagem:");
        labelImagem.setBounds(30, 170, 150, 25);
        add(labelImagem);
        campoImagem = new JTextField();
        campoImagem.setBounds(180, 170, 140, 25);
        campoImagem.setEditable(false);
        add(campoImagem);

        botaoEscolher = new JButton("Escolher...");
        botaoEscolher.setBounds(325, 170, 75, 25);
        add(botaoEscolher);
        botaoEscolher.addActionListener(e -> escolherImagem());

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(120, 240, 180, 40);
        add(botaoSalvar);
        botaoSalvar.addActionListener(e -> salvarProduto());

        JButton botaoVoltar = new JButton("← Voltar");
        botaoVoltar.setBounds(520, 440, 120, 35);
        add(botaoVoltar);
        botaoVoltar.addActionListener(e -> {
            limparCampos();
            principal.mostrarTela("LISTAR_PRODUTOS");
        });
    }

    private void escolherImagem() {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Escolher imagem do produto");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Imagens (jpg, png, gif, jpeg)", "jpg", "jpeg", "png", "gif");
        seletor.setFileFilter(filtro);

        int resultado = seletor.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            arquivoImagemEscolhido = seletor.getSelectedFile();
            campoImagem.setText(arquivoImagemEscolhido.getName());
        }
    }

    private void salvarProduto() {
        try {
            String codigo = campoCodigo.getText();
            String nome = campoNome.getText();
            BigDecimal valor = new BigDecimal(campoValor.getText().trim());

            if (produtoEmEdicao != null) {
                ProdutoService.atualizarProduto(produtoEmEdicao.getCodigoBarras(), nome, valor);
                JOptionPane.showMessageDialog(this, "Produto alterado com sucesso!");
            } else {
                String imagem = campoImagem.getText();
                if (arquivoImagemEscolhido != null) {
                    copiarImagemParaPasta(arquivoImagemEscolhido);
                }
                ProdutoService.cadastrarProduto(codigo, nome, valor, imagem);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            }

            limparCampos();
            principal.mostrarTela("LISTAR_PRODUTOS");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Use números, ex: 8.99", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void copiarImagemParaPasta(File origem) {
        try {
            File pastaImagens = new File("dados/imagens");
            if (!pastaImagens.exists()) {
                pastaImagens.mkdirs();
            }
            Path destino = new File(pastaImagens, origem.getName()).toPath();
            Files.copy(origem.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível copiar a imagem: " + e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void carregarDadosParaEdicao(Produto produto) {
        this.produtoEmEdicao = produto;
        campoCodigo.setText(produto.getCodigoBarras());
        campoCodigo.setEditable(false);
        campoNome.setText(produto.getNome());
        campoValor.setText(produto.getValor().toString());
        campoImagem.setText(produto.getLinkImagem());
        botaoEscolher.setEnabled(false);
    }

    public void limparCampos() {
        campoCodigo.setText("");
        campoCodigo.setEditable(true);
        campoNome.setText("");
        campoValor.setText("");
        campoImagem.setText("");
        arquivoImagemEscolhido = null;
        produtoEmEdicao = null;
        botaoEscolher.setEnabled(true);
    }
}
