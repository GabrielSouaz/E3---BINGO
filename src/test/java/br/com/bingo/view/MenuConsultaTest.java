package br.com.bingo.view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.model.Cartela;
import br.com.bingo.model.Cliente;
import br.com.bingo.repository.CartelaRepositorio;
import br.com.bingo.service.ClienteService;
import br.com.bingo.service.ConsultaCartelaService;
import br.com.bingo.service.ConsultaClienteService;

class MenuConsultaTest {

    private ConsultaClienteService consultaClientes;
    private ConsultaCartelaService consultaCartelas;

    @BeforeEach
    void setUp() {
        ClienteService clientes = new ClienteService();
        Cliente maria = new Cliente(1, "Maria Silva", "12345678901", "31999990000");
        clientes.cadastrar(maria);
        clientes.cadastrar(new Cliente(2, "Joao Souza", "98765432100", "31988880000"));

        CartelaRepositorio cartelas = new CartelaRepositorio();
        cartelas.adicionar(new Cartela(1, maria, 5.0, 1));

        consultaClientes = new ConsultaClienteService(clientes);
        consultaCartelas = new ConsultaCartelaService(cartelas);
    }

    private String executar(String entrada) {
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        MenuConsulta menu = new MenuConsulta(consultaClientes, consultaCartelas,
                new Scanner(entrada), new PrintStream(saida));
        menu.exibir();
        return saida.toString();
    }

    @Test
    void deveListarTodosOsClientes() {
        String saida = executar("1\n0\n");
        assertTrue(saida.contains("Maria Silva"));
        assertTrue(saida.contains("Joao Souza"));
    }

    @Test
    void deveBuscarClientePorId() {
        String saida = executar("2\n2\n0\n");
        assertTrue(saida.contains("Joao Souza"));
    }

    @Test
    void deveMostrarMensagemQuandoClienteNaoEncontrado() {
        String saida = executar("2\n99\n0\n");
        assertTrue(saida.contains("nao encontrado"));
    }

    @Test
    void deveMostrarMensagemParaIdNaoNumerico() {
        String saida = executar("2\nabc\n0\n");
        assertTrue(saida.contains("Valor invalido"));
    }

    @Test
    void deveBuscarClientesPorNome() {
        String saida = executar("3\nmaria\n0\n");
        assertTrue(saida.contains("Maria Silva"));
        assertFalse(saida.contains("Joao Souza"));
    }

    @Test
    void deveListarCartelasDeUmCliente() {
        String saida = executar("5\n1\n0\n");
        assertTrue(saida.contains("Cartela #1"));
    }

    @Test
    void deveMostrarTotalVendido() {
        String saida = executar("6\n0\n");
        assertTrue(saida.contains("5,00") || saida.contains("5.00"));
    }

    @Test
    void deveInformarOpcaoInvalida() {
        String saida = executar("42\n0\n");
        assertTrue(saida.contains("Opcao invalida"));
    }
}
