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
}