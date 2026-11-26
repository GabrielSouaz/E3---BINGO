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
    public void deveCadastrarClienteValido() {
    Cliente cliente = new Cliente(
        1, "Ana Silva", "12345678901", "31999999999"
    );

    service.cadastrar(cliente);

    assertTrue(service.buscarPorId(1).isPresent());
    assertEquals(cliente, service.buscarPorId(1).get());
    }

    @Test
    public void naoDeveCadastrarClienteComIdDuplicado() {
    Cliente cliente1 = new Cliente(
        1, "Ana Silva", "12345678901", "31999999999"
    );

    Cliente cliente2 = new Cliente(
        1, "Maria Souza", "98765432100", "31988888888"
    );

    service.cadastrar(cliente1);

    assertThrows(IllegalArgumentException.class, () -> {
        service.cadastrar(cliente2);
    });

    assertEquals(cliente1, service.buscarPorId(1).get());
    }

    @Test
    public void naoDeveCadastrarClienteNulo() {
    assertThrows(IllegalArgumentException.class, () -> {
        service.cadastrar(null);
    });
    }
    @Test
    public void deveExcluirClienteExistente() {
        Cliente cliente = new Cliente("1", "João Silva", false);
        service.cadastrar(cliente);
        
        service.excluir(1);
        
        assertFalse(service.buscarPorId(1).isPresent(), "O cliente deveria ter sido removido.");
    }

    @Test
    public void naoDeveExcluirClienteComPendenciaFinanceira() {
        Cliente cliente = new Cliente("2", "Maria Souza", true);
        service.cadastrar(cliente);
        
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.excluir(2);
        });
        
        assertEquals("Não é possível excluir um cliente com pendências financeiras.", exception.getMessage());
        assertTrue(service.buscarPorId(2).isPresent(), "O cliente não deveria ter sido removido.");
    }

    @Test
    public void deveLancarErroAoExcluirClienteInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.excluir(999);
        });
        
        assertEquals("Cliente não encontrado para exclusão.", exception.getMessage());
    }
}