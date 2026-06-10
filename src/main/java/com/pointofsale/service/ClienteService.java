package com.pointofsale.service;

import com.pointofsale.dao.ClienteDAO;
import com.pointofsale.model.Cliente;

public class ClienteService {

    public static void cadastrarNovoCliente(long cpf) {

        if (ClienteDAO.buscarPorCpf(cpf) != null) {
            throw new IllegalArgumentException("Erro: Este CPF já está cadastrado no sistema!");
        }

        long novoId = ClienteDAO.gerarProximoId();
        Cliente novoCliente = new Cliente(novoId, cpf);

        ClienteDAO.salvarCliente(novoCliente);
    }
}
