package br.com.bingo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartelaTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Maria Silva", "12345678901", "31999990000");
    }

    @Test
    void deveCriarCartelaComDadosValidos() {
        Cartela cartela = new Cartela(10, cliente, 5.0, 2);

        assertEquals(10, cartela.getId());
        assertSame(cliente, cartela.getCliente());
        assertEquals(5.0, cartela.getValor(), 0.001);
        assertEquals(2, cartela.getNumeroRodada());
    }

    @Test
    void deveAceitarValoresLimiteMinimos() {
        Cartela cartela = new Cartela(1, cliente, 0.01, 1);
        assertEquals(0.01, cartela.getValor(), 0.0001);
        assertEquals(1, cartela.getNumeroRodada());
    }

    @Test
    void naoDeveAceitarIdZero() {
        assertThrows(IllegalArgumentException.class, () -> new Cartela(0, cliente, 5.0, 1));
    }

    @Test
    void naoDeveAceitarClienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Cartela(1, null, 5.0, 1));
    }

    @Test
    void naoDeveAceitarValorZero() {
        assertThrows(IllegalArgumentException.class, () -> new Cartela(1, cliente, 0.0, 1));
    }

    @Test
    void naoDeveAceitarRodadaZero() {
        assertThrows(IllegalArgumentException.class, () -> new Cartela(1, cliente, 5.0, 0));
    }
}
