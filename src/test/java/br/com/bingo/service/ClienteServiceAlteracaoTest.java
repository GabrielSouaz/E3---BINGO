package br.com.bingo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cliente;

class ClienteServiceAlteracaoTest {

    private ClienteService service;

    @BeforeEach
    void setUp() {
        service = new ClienteService();
        service.cadastrar(new Cliente(1, "Ana Silva", "12345678901", "31999999999"));
        service.cadastrar(new Cliente(2, "Bruno Lima", "98765432100", "31977776666"));
    }

    @Test
    void deveAlterarDadosDoClienteExistente() {
        service.alterar(1, "Ana Souza", "10987654321", "31988887777");

        Cliente alterado = service.buscarPorId(1).get();
        assertEquals("Ana Souza", alterado.getNome());
        assertEquals("10987654321", alterado.getCpf());
        assertEquals("31988887777", alterado.getTelefone());
    }

    @Test
    void naoDeveAlterarOsDadosDeOutrosClientes() {
        service.alterar(1, "Ana Souza", "10987654321", "31988887777");

        Cliente outro = service.buscarPorId(2).get();
        assertEquals("Bruno Lima", outro.getNome());
        assertEquals("98765432100", outro.getCpf());
        assertEquals("31977776666", outro.getTelefone());
    }

    @Test
    void naoDeveAlterarClienteInexistente() {
        RegistroNaoEncontradoException erro = assertThrows(RegistroNaoEncontradoException.class,
                () -> service.alterar(99, "Ana Souza", "10987654321", "31988887777"));

        assertTrue(erro.getMessage().contains("99"));
    }

    @Test
    void naoDeveAlterarComIdZero() {
        assertThrows(IllegalArgumentException.class,
                () -> service.alterar(0, "Ana Souza", "10987654321", "31988887777"));
    }

    @Test
    void naoDeveAlterarComIdNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.alterar(-1, "Ana Souza", "10987654321", "31988887777"));
    }

    @Test
    void naoDeveAlterarParaCpfJaUsadoPorOutroCliente() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.alterar(1, "Ana Souza", "98765432100", "31988887777"));

        assertTrue(erro.getMessage().contains("CPF"));
        assertEquals("Ana Silva", service.buscarPorId(1).get().getNome());
        assertEquals("12345678901", service.buscarPorId(1).get().getCpf());
    }

    @Test
    void devePermitirManterOProprioCpfAoAlterarOutrosDados() {
        service.alterar(1, "Ana Souza", "12345678901", "31988887777");

        Cliente alterado = service.buscarPorId(1).get();
        assertEquals("Ana Souza", alterado.getNome());
        assertEquals("12345678901", alterado.getCpf());
    }

    @Test
    void naoDeveModificarClienteArmazenadoQuandoDadosForemInvalidos() {
        assertThrows(IllegalArgumentException.class,
                () -> service.alterar(1, "Ana Souza", "123", "31988887777"));

        Cliente original = service.buscarPorId(1).get();
        assertEquals("Ana Silva", original.getNome());
        assertEquals("12345678901", original.getCpf());
        assertEquals("31999999999", original.getTelefone());
    }
}
