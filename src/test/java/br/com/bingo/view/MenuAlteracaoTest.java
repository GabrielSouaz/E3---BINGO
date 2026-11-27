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

    @Test
    void deveMostrarMensagemParaIdNaoNumerico() {
        String saida = executar("abc\n");

        assertTrue(saida.contains("Valor invalido"));
        assertEquals("Ana Silva", clienteService.buscarPorId(1).get().getNome());
    }

    @Test
    void deveMostrarMensagemParaClienteInexistente() {
        String saida = executar("99\n");

        assertTrue(saida.contains("nao encontrado"));
        assertFalse(saida.contains("Cliente alterado com sucesso"));
    }

    @Test
    void deveMostrarMensagemParaIdZero() {
        String saida = executar("0\n");

        assertTrue(saida.contains("nao encontrado"));
    }

    @Test
    void deveMostrarMensagemParaCpfInvalidoSemAlterarOCliente() {
        String saida = executar("1\nAna Souza\n123\n31988887777\n");

        assertTrue(saida.contains("CPF"));
        assertFalse(saida.contains("Cliente alterado com sucesso"));
        assertEquals("Ana Silva", clienteService.buscarPorId(1).get().getNome());
        assertEquals("12345678901", clienteService.buscarPorId(1).get().getCpf());
    }

    @Test
    void deveMostrarMensagemParaCpfJaUsadoPorOutroCliente() {
        clienteService.cadastrar(new Cliente(2, "Bruno Lima", "98765432100", "31977776666"));

        String saida = executar("1\nAna Souza\n98765432100\n\n");

        assertTrue(saida.contains("Ja existe outro cliente com esse CPF"));
        assertEquals("12345678901", clienteService.buscarPorId(1).get().getCpf());
    }

    @Test
    void naoDeveQuebrarQuandoAEntradaTerminaAntesDoFim() {
        String saida = executar("1\n");

        assertTrue(saida.contains("Cliente alterado com sucesso"));
    }

    @Test
    void deveIndicarCamposNaoInformadosEmVezDeNull() {
        clienteService.cadastrar(new Cliente("5", "Cliente Mock", false));

        String saida = executar("5\n\n\n\n");

        assertTrue(saida.contains("CPF [nao informado]"));
        assertTrue(saida.contains("Telefone [nao informado]"));
        assertFalse(saida.contains("[null]"));
    }
}
