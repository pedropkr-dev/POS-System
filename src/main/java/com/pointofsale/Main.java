package com.pointofsale;

import com.pointofsale.service.ValidadorCPF;

public class Main {
    public static void main(String[] args) {

        boolean meuCpf = ValidadorCPF.validar(52998224725L);
        boolean meuCpfAleatorio = ValidadorCPF.validar(12375839223L);
        boolean meuCpfInvalido = ValidadorCPF.validar(11111111111L);

        System.out.println(meuCpf);
        System.out.println(meuCpfAleatorio);
        System.out.println(meuCpfInvalido);
    }
}
