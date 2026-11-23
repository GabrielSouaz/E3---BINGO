package br.com.bingo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("Cadastrar: funcionalidade em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Consultar: funcionalidade em desenvolvimento.");
                    break;
                case 3:
                    System.out.println("Alterar: funcionalidade em desenvolvimento.");
                    break;
                case 4:
                    System.out.println("Excluir: funcionalidade em desenvolvimento.");
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
