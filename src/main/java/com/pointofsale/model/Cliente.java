package com.pointofsale.model;

import com.pointofsale.service.ValidadorCPF;

public class Cliente {
    private long CPF;

    public Cliente(long CPF) {
        if (!ValidadorCPF.validar(CPF)) {
            throw new IllegalArgumentException("Erro: O CPF informado é inválido.");
        }
        this.CPF = CPF;
    }

    public long getCPF() {
        return CPF;
    }
}
