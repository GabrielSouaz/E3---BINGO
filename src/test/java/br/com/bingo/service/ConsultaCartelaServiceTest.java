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

    // ---- listarPorRodada

    @Test
    void deveListarCartelasDeUmaRodada() {
        assertEquals(2, consulta.listarPorRodada(2).size());
    }

    @Test
    void deveRetornarListaVaziaParaRodadaSemCartelas() {
        assertTrue(consulta.listarPorRodada(9).isEmpty());
    }

    @Test
    void deveLancarExcecaoParaRodadaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> consulta.listarPorRodada(0));
    }

    // ---- calcularTotalVendido

    @Test
    void deveCalcularTotalVendidoEmCartelas() {
        assertEquals(30.0, consulta.calcularTotalVendido(), 0.001);
    }

    @Test
    void deveRetornarZeroQuandoNaoHaCartelasVendidas() {
        ConsultaCartelaService vazia = new ConsultaCartelaService(new CartelaRepositorio());
        assertEquals(0.0, vazia.calcularTotalVendido(), 0.001);
    }

    @Test
    void deveSomarCentavosCorretamente() {
        CartelaRepositorio repo = new CartelaRepositorio();
        repo.adicionar(new Cartela(1, maria, 0.10, 1));
        repo.adicionar(new Cartela(2, maria, 0.20, 1));
        assertEquals(0.30, new ConsultaCartelaService(repo).calcularTotalVendido(), 0.0001);
    }

    // ---- calcularTotalVendidoPorRodada

    @Test
    void deveCalcularTotalVendidoDeUmaRodada() {
        assertEquals(15.0, consulta.calcularTotalVendidoPorRodada(1), 0.001);
    }

    @Test
    void deveRetornarZeroParaRodadaSemVendas() {
        assertEquals(0.0, consulta.calcularTotalVendidoPorRodada(5), 0.001);
    }

    @Test
    void deveLancarExcecaoAoCalcularRodadaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> consulta.calcularTotalVendidoPorRodada(-3));
    }

    // ---- listarTodas

    @Test
    void deveListarTodasAsCartelasOrdenadasPorId() {
        CartelaRepositorio repo = new CartelaRepositorio();
        repo.adicionar(new Cartela(8, joao, 5.0, 1));
        repo.adicionar(new Cartela(2, maria, 5.0, 1));
        List<Cartela> todas = new ConsultaCartelaService(repo).listarTodas();
        assertEquals(2, todas.get(0).getId());
        assertEquals(8, todas.get(1).getId());
    }

    @Test
    void deveListarQuantidadeCorretaDeCartelas() {
        assertEquals(4, consulta.listarTodas().size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaCartelas() {
        assertTrue(new ConsultaCartelaService(new CartelaRepositorio()).listarTodas().isEmpty());
    }

    // ---- construtor

    @Test
    void naoDeveCriarConsultaComRepositorioNulo() {
        assertThrows(IllegalArgumentException.class, () -> new ConsultaCartelaService(null));
    }
}
