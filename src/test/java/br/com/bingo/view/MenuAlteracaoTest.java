package br.com.bingo.view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.model.Cliente;
import br.com.bingo.service.ClienteService;

class MenuAlteracaoTest {

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
        clienteService.cadastrar(new Cliente(1, "Ana Silva", "12345678901", "31999999999"));
    }

    private String executar(String entrada) {
        ByteArrayOutputStream saida = new ByteArrayOutputStream();

        MenuAlteracao menu = new MenuAlteracao(
                clienteService,
                new Scanner(entrada),
                new PrintStream(saida));

        menu.exibir();

        return saida.toString();
    }

    @Test
    void deveAlterarClienteExistente() {
        String saida = executar("1\nAna Souza\n10987654321\n31988887777\n");

        Cliente alterado = clienteService.buscarPorId(1).get();
        assertEquals("Ana Souza", alterado.getNome());
        assertEquals("10987654321", alterado.getCpf());
        assertEquals("31988887777", alterado.getTelefone());
        assertTrue(saida.contains("Cliente alterado com sucesso"));
    }

    @Test
    void deveManterDadosQuandoTodosOsCamposFicamEmBranco() {
        String saida = executar("1\n\n\n\n");

        Cliente cliente = clienteService.buscarPorId(1).get();
        assertEquals("Ana Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("31999999999", cliente.getTelefone());
        assertTrue(saida.contains("Cliente alterado com sucesso"));
    }

    @Test
    void deveAlterarSomenteOsCamposInformados() {
        executar("1\nAna Souza\n\n\n");

        Cliente cliente = clienteService.buscarPorId(1).get();
        assertEquals("Ana Souza", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("31999999999", cliente.getTelefone());
    }
}
