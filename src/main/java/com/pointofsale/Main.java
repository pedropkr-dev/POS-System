package com.pointofsale;

import com.pointofsale.service.ValidadorCPF;

import java.util.Scanner;

import static java.util.Collections.replaceAll;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("DIGITE SEU CPF:");
            String cpfPontuado  = scan.nextLine();


            String cpfLimpo = cpfPontuado.replaceAll("[^0-9]", "");
            boolean resultado = ValidadorCPF.validar(Long.parseLong(cpfLimpo));

            if (resultado == true) {
                System.out.println("CPF Válido");
                break;
            }
            else {
                System.out.println("CPF Inválido");
            }
        }

    }
}
