package br.com.bingo;

import java.util.Scanner;

import br.com.bingo.view.MenuAlteracao;
import br.com.bingo.view.MenuCadastro;

import br.com.bingo.repository.CartelaRepositorio;
import br.com.bingo.repository.ClienteRepositorio;
import br.com.bingo.service.ConsultaCartelaService;
import br.com.bingo.service.ConsultaClienteService;
import br.com.bingo.view.MenuConsulta;

import br.com.bingo.model.Cliente;
import br.com.bingo.service.ClienteService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
        CartelaRepositorio cartelaRepositorio = new CartelaRepositorio();
        MenuConsulta menuConsulta = new MenuConsulta(
                new ConsultaClienteService(clienteRepositorio),
                new ConsultaCartelaService(cartelaRepositorio),
                scanner, System.out);

        ClienteService clienteService = new ClienteService();
        MenuCadastro menuCadastro = new MenuCadastro(
            clienteService, scanner, System.out);
        MenuAlteracao menuAlteracao = new MenuAlteracao(
            clienteService, scanner, System.out);

        clienteService.cadastrar(new Cliente("1", "Cliente Teste", false));
        clienteService.cadastrar(new Cliente("2", "Cliente Devedor", true));

        int opcao;

        do {
            System.out.println("===== SISTEMA DE BINGO =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Consultar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    menuCadastro.exibir();
                    break;
                case 2:
                    menuConsulta.exibir();
                    break;
                case 3:
                    menuAlteracao.exibir();
                    break;
                case 4:
                    // Seu código de exclusão
                    System.out.print("Digite o ID do cliente a ser excluído: ");
                    int idExclusao = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    try {
                        clienteService.excluir(idExclusao);
                        System.out.println("Cliente excluído com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }
}