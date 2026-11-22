package br.com.bingo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void deveCriarClienteComDadosValidos() {
        Cliente cliente = new Cliente(1, "Maria Silva", "12345678901", "31999990000");

        assertEquals(1, cliente.getId());
        assertEquals("Maria Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("31999990000", cliente.getTelefone());
    }

    @Test
    void deveAceitarIdLimiteIgualAUm() {
        Cliente cliente = new Cliente(1, "Joao", "11122233344", "3133334444");
        assertEquals(1, cliente.getId());
    }

    @Test
    void naoDeveAceitarIdZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(0, "Joao", "11122233344", "3133334444"));
    }

    @Test
    void naoDeveAceitarIdNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(-5, "Joao", "11122233344", "3133334444"));
    }

    @Test
    void naoDeveAceitarNomeNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1, null, "11122233344", "3133334444"));
    }

    @Test
    void naoDeveAceitarNomeEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1, "   ", "11122233344", "3133334444"));
    }

    @Test
    void naoDeveAceitarCpfComMenosDeOnzeDigitos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1, "Joao", "1234567890", "3133334444"));
    }

    @Test
    void naoDeveAceitarCpfNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1, "Joao", null, "3133334444"));
    }
}
