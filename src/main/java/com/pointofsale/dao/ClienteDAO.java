package com.pointofsale.dao;

import com.pointofsale.model.Cliente;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public static void salvarCliente(Cliente cliente) {
        File arquivo = new File("dados/clientes.csv");

        try (FileWriter writer = new FileWriter(arquivo, true)) {

            String linhasCSV = cliente.getId_cliente() + ";" +
                               cliente.getCpf() + "\n";

        } catch (IOException e){
            System.out.println("Erro. Não foi possível acessar o cliente.");
            e.printStackTrace();
        }
    }

    public static List<Cliente> listarClientes() {

        List<Cliente> catalogo = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(Paths.get("dados/clientes.csv"));

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] fatias = linha.split(";");

                Long id_cliente = Long.valueOf(fatias[0]);
                Long cpf = Long.valueOf(fatias[1]);

                Cliente c = new Cliente(id_cliente, cpf);

                catalogo.add(c);
            }

        } catch (IOException e) {

            System.out.println("Erro. Nenhum cliente encontrado.");

        }

        return catalogo;

    }

    public static Cliente buscarPorCpf(long cpfBuscado) {
        List<Cliente> todosClientes = listarClientes();

        for (Cliente c : todosClientes) {
            if (c.getCpf() == cpfBuscado) {
                return c;
            }
        }

        return null;
    }

    public static long gerarProximoId() {
        List<Cliente> clientes = listarClientes();
        if (clientes.isEmpty()) {
            return 1;
        }
        Cliente ultimoCliente = clientes.get(clientes.size() - 1);
        return ultimoCliente.getId_cliente() + 1;
    }

}
