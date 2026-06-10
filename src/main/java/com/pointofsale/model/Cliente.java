package com.pointofsale.model;

import com.pointofsale.service.ValidadorCPF;

public class Cliente {
    private long id_cliente;
    private long cpf;

    public Cliente(long id_cliente, long cpf) {
        if (!ValidadorCPF.validar(cpf)) {
            throw new IllegalArgumentException("Erro: O CPF informado é inválido.");
        }
        this.cpf = cpf;
        this.id_cliente = id_cliente;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public long getCpf() {
        return cpf;
    }
}
