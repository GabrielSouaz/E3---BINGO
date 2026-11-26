package br.com.bingo.view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.service.ClienteService;

class MenuCadastroTest {

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
    }

    private String executar(String entrada) {
        ByteArrayOutputStream saida = new ByteArrayOutputStream();

        MenuCadastro menu = new MenuCadastro(
                clienteService,
                new Scanner(entrada),
                new PrintStream(saida));

        menu.exibir();

        return saida.toString();
    }

    @Test
    void deveCadastrarClienteValido() {
        String saida = executar(
                "1\nAna Silva\n12345678901\n31999999999\n");

        assertTrue(clienteService.buscarPorId(1).isPresent());
        assertTrue(saida.contains("Cliente cadastrado com sucesso"));
    }
    @Test
    void deveMostrarMensagemParaIdNaoNumerico() {
        String saida = executar("abc\n");

        assertTrue(saida.contains("Valor invalido"));
        assertFalse(clienteService.buscarPorId(1).isPresent());
    }
    @Test
    void deveMostrarMensagemParaIdDuplicado() {
        executar("1\nAna Silva\n12345678901\n31999999999\n");

        String saida = executar(
            "1\nMaria Souza\n98765432100\n31988888888\n");

        assertTrue(saida.contains("Ja existe um cliente com esse id."));
        assertEquals("Ana Silva",
            clienteService.buscarPorId(1).get().getNome());
    }
    @Test
    void deveMostrarMensagemParaCpfInvalido() {
        String saida = executar(
            "1\nAna Silva\n123\n31999999999\n");

        assertTrue(saida.contains(
            "O CPF deve conter 11 digitos numericos."));

        assertFalse(clienteService.buscarPorId(1).isPresent());
    }
}