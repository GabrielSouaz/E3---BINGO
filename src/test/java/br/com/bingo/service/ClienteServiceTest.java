package br.com.bingo.service;

import br.com.bingo.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    private ClienteService service;

    @BeforeEach
    public void setup() {
        service = new ClienteService();
    }

    @Test
    public void deveExcluirClienteExistente() {
        Cliente cliente = new Cliente("1", "João Silva", false);
        service.cadastrar(cliente);
        service.excluir("1");
        assertFalse(service.buscarPorId("1").isPresent(), "O cliente deveria ter sido removido.");
    }

    @Test
    public void naoDeveExcluirClienteComPendenciaFinanceira() {
        Cliente cliente = new Cliente("2", "Maria Souza", true);
        service.cadastrar(cliente);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.excluir("2");
        });
        assertEquals("Não é possível excluir um cliente com pendências financeiras.", exception.getMessage());
        assertTrue(service.buscarPorId("2").isPresent(), "O cliente não deveria ter sido removido.");
    }

    @Test
    public void deveLancarErroAoExcluirClienteInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.excluir("999");
        });
        assertEquals("Cliente não encontrado para exclusão.", exception.getMessage());
    }
}