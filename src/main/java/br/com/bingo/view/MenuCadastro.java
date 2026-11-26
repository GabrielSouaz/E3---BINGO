package br.com.bingo.view;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.bingo.service.ClienteService;

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
    }
}