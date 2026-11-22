package br.com.bingo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.model.Cartela;
import br.com.bingo.model.Cliente;

class CartelaRepositorioTest {

    private CartelaRepositorio repositorio;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        repositorio = new CartelaRepositorio();
        cliente = new Cliente(1, "Ana", "11111111111", "31911111111");
    }

    // ---- adicionar

    @Test
    void deveAdicionarCartela() {
        repositorio.adicionar(new Cartela(1, cliente, 5.0, 1));
        assertEquals(1, repositorio.listarTodas().size());
    }

    @Test
    void naoDeveAdicionarCartelaNula() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.adicionar(null));
    }

    @Test
    void naoDeveAdicionarCartelaComIdRepetido() {
        repositorio.adicionar(new Cartela(1, cliente, 5.0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.adicionar(new Cartela(1, cliente, 10.0, 2)));
    }

    // ---- listarTodas

    @Test
    void deveRetornarListaVaziaSemCartelas() {
        assertTrue(repositorio.listarTodas().isEmpty());
    }

    @Test
    void deveListarTodasAsCartelas() {
        repositorio.adicionar(new Cartela(1, cliente, 5.0, 1));
        repositorio.adicionar(new Cartela(2, cliente, 5.0, 1));
        assertEquals(2, repositorio.listarTodas().size());
    }

    @Test
    void listaDeCartelasNaoDevePermitirAlteracao() {
        assertThrows(UnsupportedOperationException.class, () -> repositorio.listarTodas().clear());
    }

    // ---- buscarPorId

    @Test
    void deveEncontrarCartelaPorId() {
        Cartela cartela = new Cartela(3, cliente, 5.0, 1);
        repositorio.adicionar(cartela);
        assertSame(cartela, repositorio.buscarPorId(3).orElseThrow());
    }

    @Test
    void deveRetornarVazioParaCartelaInexistente() {
        assertTrue(repositorio.buscarPorId(42).isEmpty());
    }

    @Test
    void deveRetornarVazioParaIdNegativo() {
        assertTrue(repositorio.buscarPorId(-1).isEmpty());
    }
}
