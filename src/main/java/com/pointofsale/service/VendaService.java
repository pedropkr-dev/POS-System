package com.pointofsale.service;

import com.pointofsale.dao.VendaDAO;
import com.pointofsale.model.Cliente;
import com.pointofsale.model.ProdutoVenda;
import com.pointofsale.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaService {

    public static void processarVenda(Cliente cliente, String formaPagamento, List<ProdutoVenda> carrinho) {

        if (cliente == null) {
            throw new IllegalArgumentException("Erro: É obrigatório vincular um cliente à venda.");
        }

        if (carrinho == null || carrinho.isEmpty()) {
            throw new IllegalArgumentException("Erro: Não é possível concluir uma venda com o carrinho vazio.");
        }

        if (formaPagamento == null || formaPagamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: A forma de pagamento deve ser informada.");
        }

        BigDecimal totalVenda = BigDecimal.ZERO;
        for (ProdutoVenda item : carrinho) {
            totalVenda = totalVenda.add(item.getSubtotal());
        }

        long idVendaGerado = VendaDAO.gerarProximoId();

        Venda novaVenda = new Venda(idVendaGerado, cliente, formaPagamento, carrinho, LocalDateTime.now(), totalVenda);

        VendaDAO.salvarVenda(novaVenda);
    }
}
