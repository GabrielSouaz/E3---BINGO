package br.com.bingo;

import br.com.bingo.model.Cliente;
import br.com.bingo.service.ClienteService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteService clienteService = new ClienteService();
        int opcao;
        
                clienteService.cadastrar(new Cliente("1", "Cliente Teste", false));
        clienteService.cadastrar(new Cliente("2", "Cliente Devedor", true));
    }
}