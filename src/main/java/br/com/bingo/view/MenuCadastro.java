package br.com.bingo.view;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.bingo.service.ClienteService;
import br.com.bingo.model.Cliente;

public class MenuCadastro {

    private final ClienteService clienteService;
    private final Scanner entrada;
    private final PrintStream saida;

    public MenuCadastro(ClienteService clienteService,
                        Scanner entrada,
                        PrintStream saida) {
        this.clienteService = clienteService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void exibir() {
    saida.println("----- CADASTRAR CLIENTE -----");

    saida.print("Id do cliente: ");
    int id = Integer.parseInt(entrada.nextLine());

    saida.print("Nome: ");
    String nome = entrada.nextLine();

    saida.print("CPF: ");
    String cpf = entrada.nextLine();

    saida.print("Telefone: ");
    String telefone = entrada.nextLine();

    Cliente cliente = new Cliente(id, nome, cpf, telefone);

    clienteService.cadastrar(cliente);

    saida.println("Cliente cadastrado com sucesso!");
    }
}