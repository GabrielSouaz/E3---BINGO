package br.com.bingo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.model.Cliente;

class ClienteServiceListagemTest {

    private ClienteService service;

    @BeforeEach
    void setUp() {
        service = new ClienteService();
    }

    @Test
    void deveListarTodosOsClientesCadastrados() {
        service.cadastrar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        service.cadastrar(new Cliente(2, "Bia", "22222222222", "31922222222"));

        assertEquals(2, service.listarTodos().size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() {
        assertTrue(service.listarTodos().isEmpty());
    }

    @Test
    void naoDeveListarClienteExcluido() {
        service.cadastrar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        service.cadastrar(new Cliente(2, "Bia", "22222222222", "31922222222"));
        service.excluir(1);

        List<Cliente> clientes = service.listarTodos();
        assertEquals(1, clientes.size());
        assertEquals(2, clientes.get(0).getId());
    }

    @Test
    void listaRetornadaNaoDevePermitirAlteracao() {
        service.cadastrar(new Cliente(1, "Ana", "11111111111", "31911111111"));
        assertThrows(UnsupportedOperationException.class, () -> service.listarTodos().clear());
    }
}
