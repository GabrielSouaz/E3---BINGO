package br.com.bingo.service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cliente;

public class ConsultaClienteService {

    private final ClienteService clienteService;

    public ConsultaClienteService(ClienteService clienteService) {
        if (clienteService == null) {
            throw new IllegalArgumentException("O servico de clientes e obrigatorio.");
        }
        this.clienteService = clienteService;
    }

    public Cliente buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id deve ser maior que zero.");
        }
        return clienteService.buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente com id " + id + " nao encontrado."));
    }

    public Cliente buscarPorCpf(String cpf) {
        String somenteNumeros = limparCpf(cpf);
        return clienteService.listarTodos().stream()
                .filter(cliente -> somenteNumeros.equals(cliente.getCpf()))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente com CPF " + cpf + " nao encontrado."));
    }

    public List<Cliente> buscarPorNome(String termo) {
        if (termo == null || termo.isBlank()) {
            throw new IllegalArgumentException("Informe um nome para a busca.");
        }
        String termoNormalizado = normalizar(termo.trim());
        return clienteService.listarTodos().stream()
                .filter(cliente -> normalizar(cliente.getNome()).contains(termoNormalizado))
                .sorted(Comparator.comparing(Cliente::getNome))
                .collect(Collectors.toList());
    }

    public List<Cliente> listarTodos() {
        return clienteService.listarTodos().stream()
                .sorted(Comparator.comparingInt(Cliente::getId))
                .collect(Collectors.toList());
    }

    public List<Cliente> listarOrdenadoPorNome() {
        return clienteService.listarTodos().stream()
                .sorted(Comparator.comparing(Cliente::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public int contarClientes() {
        return clienteService.listarTodos().size();
    }

    public boolean existeCliente(int id) {
        return clienteService.buscarPorId(id).isPresent();
    }

    private static String limparCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("O CPF e obrigatorio.");
        }
        String somenteNumeros = cpf.replaceAll("[^0-9]", "");
        if (somenteNumeros.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 digitos.");
        }
        return somenteNumeros;
    }

    private static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
