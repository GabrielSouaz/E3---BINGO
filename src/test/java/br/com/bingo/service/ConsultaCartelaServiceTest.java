package br.com.bingo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cartela;
import br.com.bingo.model.Cliente;
import br.com.bingo.repository.CartelaRepositorio;

class ConsultaCartelaServiceTest {

    private CartelaRepositorio repositorio;
    private ConsultaCartelaService consulta;
    private Cliente maria;
    private Cliente joao;

    @BeforeEach
    void setUp() {
        maria = new Cliente(1, "Maria Silva", "12345678901", "31999990000");
        joao = new Cliente(2, "Joao Souza", "98765432100", "31988880000");

        repositorio = new CartelaRepositorio();
        repositorio.adicionar(new Cartela(1, maria, 5.0, 1));
        repositorio.adicionar(new Cartela(2, maria, 5.0, 2));
        repositorio.adicionar(new Cartela(3, joao, 10.0, 1));
        repositorio.adicionar(new Cartela(4, maria, 10.0, 2));
        consulta = new ConsultaCartelaService(repositorio);
    }

    // ---- buscarPorId

    @Test
    void deveBuscarCartelaPorId() {
        assertEquals("Joao Souza", consulta.buscarPorId(3).getCliente().getNome());
    }

    @Test
    void deveBuscarCartelaComIdLimiteUm() {
        assertEquals(1, consulta.buscarPorId(1).getId());
    }

    @Test
    void deveLancarExcecaoParaCartelaInexistente() {
        assertThrows(RegistroNaoEncontradoException.class, () -> consulta.buscarPorId(100));
    }

    @Test
    void deveLancarExcecaoParaIdDeCartelaInvalido() {
        assertThrows(IllegalArgumentException.class, () -> consulta.buscarPorId(0));
    }

    // ---- listarPorCliente

    @Test
    void deveListarCartelasDeUmCliente() {
        List<Cartela> cartelas = consulta.listarPorCliente(1);
        assertEquals(3, cartelas.size());
        assertTrue(cartelas.stream().allMatch(c -> c.getCliente().getId() == 1));
    }

    @Test
    void deveRetornarListaVaziaParaClienteSemCartelas() {
        assertTrue(consulta.listarPorCliente(77).isEmpty());
    }

    @Test
    void deveLancarExcecaoParaIdDeClienteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> consulta.listarPorCliente(-1));
    }
}
