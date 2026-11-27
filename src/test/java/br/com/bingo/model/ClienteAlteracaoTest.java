package br.com.bingo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteAlteracaoTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Ana Silva", "12345678901", "31999999999");
    }

    @Test
    void deveAtualizarNomeCpfETelefone() {
        cliente.atualizarDados("Ana Souza", "10987654321", "31988887777");

        assertEquals("Ana Souza", cliente.getNome());
        assertEquals("10987654321", cliente.getCpf());
        assertEquals("31988887777", cliente.getTelefone());
    }

    @Test
    void deveManterIdEDividaAoAtualizarDados() {
        cliente.setPossuiDivida(true);

        cliente.atualizarDados("Ana Souza", "10987654321", "31988887777");

        assertEquals(1, cliente.getId());
        assertTrue(cliente.isPossuiDivida());
    }

    @Test
    void deveRemoverEspacosExtrasDoNome() {
        cliente.atualizarDados("  Ana Souza  ", "12345678901", "31999999999");

        assertEquals("Ana Souza", cliente.getNome());
    }

    @Test
    void naoDeveAtualizarComNomeNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados(null, "12345678901", "31999999999"));
    }

    @Test
    void naoDeveAtualizarComNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("", "12345678901", "31999999999"));
    }

    @Test
    void naoDeveAtualizarComNomeApenasEspacos() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("   ", "12345678901", "31999999999"));
    }

    @Test
    void naoDeveAtualizarComCpfNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", null, "31999999999"));
    }

    @Test
    void naoDeveAtualizarComCpfCom10Digitos() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "1234567890", "31999999999"));
    }

    @Test
    void naoDeveAtualizarComCpfCom12Digitos() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "123456789012", "31999999999"));
    }

    @Test
    void naoDeveAtualizarComCpfContendoLetras() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "1234567890a", "31999999999"));
    }

    @Test
    void deveAceitarTelefoneCom10Digitos() {
        cliente.atualizarDados("Ana Souza", "12345678901", "3133334444");

        assertEquals("3133334444", cliente.getTelefone());
    }

    @Test
    void deveAceitarTelefoneVazio() {
        cliente.atualizarDados("Ana Souza", "12345678901", "");

        assertEquals("", cliente.getTelefone());
    }

    @Test
    void naoDeveAtualizarComTelefoneCom9Digitos() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "12345678901", "313333444"));
    }

    @Test
    void naoDeveAtualizarComTelefoneCom12Digitos() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "12345678901", "319999999999"));
    }

    @Test
    void naoDeveAtualizarComTelefoneContendoLetras() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "12345678901", "3199999abc9"));
    }

    @Test
    void naoDeveAlterarNenhumCampoQuandoCpfForInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados("Ana Souza", "123", "31988887777"));

        assertEquals("Ana Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("31999999999", cliente.getTelefone());
    }

    @Test
    void naoDeveAlterarNenhumCampoQuandoNomeForInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> cliente.atualizarDados(" ", "10987654321", "31988887777"));

        assertEquals("Ana Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("31999999999", cliente.getTelefone());
    }
}
