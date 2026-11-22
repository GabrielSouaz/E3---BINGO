package br.com.bingo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

    // ---- buscarPorNome

    @Test
    void deveBuscarClientesPorParteDoNome() {
        List<Cliente> encontrados = consulta.buscarPorNome("maria");
        assertEquals(2, encontrados.size());
    }

    @Test
    void deveIgnorarMaiusculasEAcentosNaBuscaPorNome() {
        repositorio.adicionar(new Cliente(4, "José Antônio", "55566677788", "31966660000"));
        assertEquals(4, consulta.buscarPorNome("JOSE ANTONIO").get(0).getId());
    }

    @Test
    void deveRetornarResultadoOrdenadoPorNome() {
        List<Cliente> encontrados = consulta.buscarPorNome("Maria");
        assertEquals("Ana Maria Costa", encontrados.get(0).getNome());
        assertEquals("Maria Silva", encontrados.get(1).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNenhumNomeCorresponde() {
        assertTrue(consulta.buscarPorNome("Pedro").isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoTermoDeBuscaVazio() {
        assertThrows(IllegalArgumentException.class, () -> consulta.buscarPorNome("  "));
    }

    // ---- listarTodos

    @Test
    void deveListarTodosOsClientesOrdenadosPorId() {
        List<Cliente> todos = consulta.listarTodos();
        assertEquals(3, todos.size());
        assertEquals(1, todos.get(0).getId());
        assertEquals(3, todos.get(2).getId());
    }

    @Test
    void deveOrdenarPorIdMesmoQuandoInseridosForaDeOrdem() {
        ClienteRepositorio outro = new ClienteRepositorio();
        outro.adicionar(new Cliente(7, "Zeca", "77777777777", "31900000007"));
        outro.adicionar(new Cliente(5, "Bruno", "55555555555", "31900000005"));
        List<Cliente> todos = new ConsultaClienteService(outro).listarTodos();
        assertEquals(5, todos.get(0).getId());
        assertEquals(7, todos.get(1).getId());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientesCadastrados() {
        ConsultaClienteService vazia = new ConsultaClienteService(new ClienteRepositorio());
        assertTrue(vazia.listarTodos().isEmpty());
    }

    // ---- listarOrdenadoPorNome

    @Test
    void deveListarClientesEmOrdemAlfabetica() {
        List<Cliente> todos = consulta.listarOrdenadoPorNome();
        assertEquals("Ana Maria Costa", todos.get(0).getNome());
        assertEquals("Joao Souza", todos.get(1).getNome());
        assertEquals("Maria Silva", todos.get(2).getNome());
    }

    @Test
    void deveOrdenarIgnorandoMaiusculas() {
        repositorio.adicionar(new Cliente(4, "bruno lima", "44444444444", "31900000004"));
        List<Cliente> todos = consulta.listarOrdenadoPorNome();
        assertEquals("bruno lima", todos.get(1).getNome());
    }

    @Test
    void deveRetornarListaVaziaAoOrdenarSemClientes() {
        ConsultaClienteService vazia = new ConsultaClienteService(new ClienteRepositorio());
        assertTrue(vazia.listarOrdenadoPorNome().isEmpty());
    }
}
