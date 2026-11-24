package br.com.bingo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.model.Cliente;

class ClienteRepositorioTest {

    private ClienteRepositorio repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ClienteRepositorio();
    }

    // ---- adicionar

    @Test
    void deveAdicionarCliente() {
        repositorio.adicionar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        assertEquals(1, repositorio.listarTodos().size());
    }

    @Test
    void naoDeveAdicionarClienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.adicionar(null));
    }

    @Test
    void naoDeveAdicionarClienteComIdRepetido() {
        repositorio.adicionar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.adicionar(new Cliente(1, "Bia", "22222222222", "31922222222")));
    }

    // ---- listarTodos

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() {
        assertTrue(repositorio.listarTodos().isEmpty());
    }

    @Test
    void deveListarClientesNaOrdemDeInsercao() {
        repositorio.adicionar(new Cliente(2, "Bia", "22222222222", "31922222222"));
        repositorio.adicionar(new Cliente(1, "Ana", "11111111111", "31911111111"));

        List<Cliente> clientes = repositorio.listarTodos();
        assertEquals(2, clientes.get(0).getId());
        assertEquals(1, clientes.get(1).getId());
    }

    @Test
    void listaRetornadaNaoDevePermitirAlteracao() {
        repositorio.adicionar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        assertThrows(UnsupportedOperationException.class, () -> repositorio.listarTodos().clear());
    }

    // ---- buscarPorId

    @Test
    void deveEncontrarClientePorId() {
        Cliente ana = new Cliente(1, "Ana", "11111111111", "31911111111");
        repositorio.adicionar(ana);
        assertEquals(Optional.of(ana), repositorio.buscarPorId(1));
    }

    @Test
    void deveRetornarVazioParaIdInexistente() {
        repositorio.adicionar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        assertTrue(repositorio.buscarPorId(99).isEmpty());
    }

    @Test
    void deveRetornarVazioParaIdZero() {
        assertTrue(repositorio.buscarPorId(0).isEmpty());
    }
}
