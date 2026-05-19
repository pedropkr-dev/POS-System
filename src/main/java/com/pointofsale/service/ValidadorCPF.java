package com.pointofsale.service;

import com.pointofsale.model.Cliente;

import java.util.ArrayList;

public class ValidadorCPF {
    private long CPF;

    public ValidadorCPF(long CPF) {
        this.CPF = CPF;
    }

    public static boolean validar(long CPF) {
        while (true) {
            String cpfStr = Long.toString(CPF);
            int digitoUmSoma = 0;
            int jUm = 10;
            int digitoDoisSoma = 0;
            int jDois = 11;


            ArrayList<Integer> numCPF= new ArrayList<>();

            for (int i = 0; i < cpfStr.length(); i++) {
                int digito = Character.getNumericValue(cpfStr.charAt(i));
                numCPF.add(digito);
            }

            int primeiroNum = numCPF.get(0);
            boolean todosIguais = true;

            for (int i = 1; i < numCPF.size(); i++) {
                if (numCPF.get(i) != primeiroNum) {
                    todosIguais = false;
                    break;
                }
            }

            if (todosIguais) {
                return false;
            }

            for (int i = 0; i < 9; i++, jUm--) {
                int digito1 = Character.getNumericValue(cpfStr.charAt(i));

                digitoUmSoma += digito1 * jUm;
            }

            float restoDigitoUm = (digitoUmSoma * 10) % 11;

            if (restoDigitoUm == Character.getNumericValue(cpfStr.charAt(9))) {
            }
            else {
                return false;
            }

            for (int i = 0; i < 10; i++, jDois--) {
                int digito2 = Character.getNumericValue(cpfStr.charAt(i));

                digitoDoisSoma += digito2 * jDois;
            }

            float restoDigitoDois = (digitoDoisSoma * 10) % 11;

            if (restoDigitoDois == Character.getNumericValue(cpfStr.charAt(10))) {
            }
            else {
                return false;
            }

            return true;
        }
    }
}
