package br.com.bingo.view;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.bingo.model.Cliente;
import br.com.bingo.service.ClienteService;

public class MenuAlteracao {

    private final ClienteService clienteService;
    private final Scanner entrada;
    private final PrintStream saida;

    public MenuAlteracao(ClienteService clienteService,
                         Scanner entrada,
                         PrintStream saida) {
        this.clienteService = clienteService;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void exibir() {
        saida.println("----- ALTERAR CLIENTE -----");

        saida.print("Id do cliente: ");
        int id = Integer.parseInt(entrada.nextLine().trim());
        Cliente atual = clienteService.buscarPorId(id).get();

        String nome = lerCampo("Nome", atual.getNome());
        String cpf = lerCampo("CPF", atual.getCpf());
        String telefone = lerCampo("Telefone", atual.getTelefone());

        clienteService.alterar(id, nome, cpf, telefone);

        saida.println("Cliente alterado com sucesso!");
    }

    private String lerCampo(String rotulo, String valorAtual) {
        saida.print(rotulo + " [" + valorAtual + "]: ");
        String digitado = entrada.nextLine();
        return digitado.isBlank() ? valorAtual : digitado;
    }
}
