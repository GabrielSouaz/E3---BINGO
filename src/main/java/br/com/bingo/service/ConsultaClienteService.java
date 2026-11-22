package br.com.bingo.service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cliente;
import br.com.bingo.repository.ClienteRepositorio;

public class ConsultaClienteService {

    private final ClienteRepositorio repositorio;

    public ConsultaClienteService(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id deve ser maior que zero.");
        }
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente com id " + id + " nao encontrado."));
    }

    public Cliente buscarPorCpf(String cpf) {
        String somenteNumeros = limparCpf(cpf);
        return repositorio.listarTodos().stream()
                .filter(cliente -> cliente.getCpf().equals(somenteNumeros))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente com CPF " + cpf + " nao encontrado."));
    }

    public List<Cliente> buscarPorNome(String termo) {
        if (termo == null || termo.isBlank()) {
            throw new IllegalArgumentException("Informe um nome para a busca.");
        }
        String termoNormalizado = normalizar(termo.trim());
        return repositorio.listarTodos().stream()
                .filter(cliente -> normalizar(cliente.getNome()).contains(termoNormalizado))
                .sorted(Comparator.comparing(Cliente::getNome))
                .collect(Collectors.toList());
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
