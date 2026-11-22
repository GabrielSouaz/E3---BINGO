package br.com.bingo.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.service.ConsultaCartelaService;
import br.com.bingo.service.ConsultaClienteService;

public class MenuConsulta {

    private final ConsultaClienteService consultaClientes;
    private final ConsultaCartelaService consultaCartelas;
    private final Scanner entrada;
    private final PrintStream saida;

    public MenuConsulta(ConsultaClienteService consultaClientes, ConsultaCartelaService consultaCartelas,
            Scanner entrada, PrintStream saida) {
        this.consultaClientes = consultaClientes;
        this.consultaCartelas = consultaCartelas;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void exibir() {
        int opcao;
        do {
            saida.println("----- CONSULTAR -----");
            saida.println("1 - Listar todos os clientes");
            saida.println("2 - Buscar cliente por id");
            saida.println("3 - Buscar clientes por nome");
            saida.println("4 - Buscar cliente por CPF");
            saida.println("5 - Listar cartelas de um cliente");
            saida.println("6 - Total vendido em cartelas");
            saida.println("0 - Voltar");
            saida.print("Escolha uma opcao: ");

            opcao = lerInteiro();
            try {
                executarOpcao(opcao);
            } catch (RegistroNaoEncontradoException | IllegalArgumentException e) {
                saida.println(e.getMessage());
            }
            saida.println();
        } while (opcao != 0 && entrada.hasNextLine());
    }

    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                imprimir(consultaClientes.listarTodos(), "Nenhum cliente cadastrado.");
                break;
            case 2:
                saida.print("Id do cliente: ");
                saida.println(consultaClientes.buscarPorId(lerInteiroObrigatorio()));
                break;
            case 3:
                saida.print("Nome (ou parte dele): ");
                imprimir(consultaClientes.buscarPorNome(entrada.nextLine()), "Nenhum cliente encontrado.");
                break;
            case 4:
                saida.print("CPF: ");
                saida.println(consultaClientes.buscarPorCpf(entrada.nextLine()));
                break;
            case 5:
                saida.print("Id do cliente: ");
                imprimir(consultaCartelas.listarPorCliente(lerInteiroObrigatorio()), "Nenhuma cartela encontrada.");
                break;
            case 6:
                saida.printf("Total vendido: R$ %.2f%n", consultaCartelas.calcularTotalVendido());
                break;
            case 0:
                break;
            default:
                saida.println("Opcao invalida.");
        }
    }

    private void imprimir(List<?> registros, String mensagemVazia) {
        if (registros.isEmpty()) {
            saida.println(mensagemVazia);
            return;
        }
        registros.forEach(saida::println);
    }

    private int lerInteiro() {
        if (!entrada.hasNextLine()) {
            return 0;
        }
        try {
            return Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int lerInteiroObrigatorio() {
        String valor = entrada.hasNextLine() ? entrada.nextLine().trim() : "";
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor invalido: informe um numero inteiro.");
        }
    }
}
