package br.com.bingo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cliente;
import br.com.bingo.repository.ClienteRepositorio;

class ConsultaClienteServiceTest {

    private ClienteRepositorio repositorio;
    private ConsultaClienteService consulta;

    @BeforeEach
    void setUp() {
        repositorio = new ClienteRepositorio();
        repositorio.adicionar(new Cliente(1, "Maria Silva", "12345678901", "31999990000"));
        repositorio.adicionar(new Cliente(2, "Joao Souza", "98765432100", "31988880000"));
        repositorio.adicionar(new Cliente(3, "Ana Maria Costa", "11122233344", "31977770000"));
        consulta = new ConsultaClienteService(repositorio);
    }

    // ---- buscarPorId

    @Test
    void deveBuscarClienteExistentePorId() {
        Cliente cliente = consulta.buscarPorId(2);
        assertEquals("Joao Souza", cliente.getNome());
    }

    @Test
    void deveBuscarClienteComIdLimiteUm() {
        assertEquals("Maria Silva", consulta.buscarPorId(1).getNome());
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExiste() {
        RegistroNaoEncontradoException erro = assertThrows(RegistroNaoEncontradoException.class,
                () -> consulta.buscarPorId(99));
        assertTrue(erro.getMessage().contains("99"));
    }

    @Test
    void deveLancarExcecaoQuandoIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> consulta.buscarPorId(0));
    }

    // ---- buscarPorCpf

    @Test
    void deveBuscarClientePorCpfSomenteNumeros() {
        assertEquals(3, consulta.buscarPorCpf("11122233344").getId());
    }

    @Test
    void deveBuscarClientePorCpfFormatadoComPontoETraco() {
        assertEquals(1, consulta.buscarPorCpf("123.456.789-01").getId());
    }

    @Test
    void deveLancarExcecaoQuandoCpfNaoCadastrado() {
        assertThrows(RegistroNaoEncontradoException.class, () -> consulta.buscarPorCpf("00000000000"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> consulta.buscarPorCpf("123"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNulo() {
        assertThrows(IllegalArgumentException.class, () -> consulta.buscarPorCpf(null));
    }
}
