package br.com.bingo.service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        if (cpf == null) {
            throw new IllegalArgumentException("O CPF e obrigatorio.");
        }
        String somenteNumeros = cpf.replaceAll("[^0-9]", "");
        if (somenteNumeros.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 digitos.");
        }
        for (Cliente cliente : repositorio.listarTodos()) {
            if (cliente.getCpf().equals(somenteNumeros)) {
                return cliente;
            }
        }
        throw new RegistroNaoEncontradoException("Cliente com CPF " + cpf + " nao encontrado.");
    }

    public List<Cliente> buscarPorNome(String termo) {
        if (termo == null || termo.isBlank()) {
            throw new IllegalArgumentException("Informe um nome para a busca.");
        }
        String termoNormalizado = Normalizer.normalize(termo.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase();
        List<Cliente> encontrados = new ArrayList<>();
        for (Cliente cliente : repositorio.listarTodos()) {
            String nomeNormalizado = Normalizer.normalize(cliente.getNome(), Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "").toLowerCase();
            if (nomeNormalizado.contains(termoNormalizado)) {
                encontrados.add(cliente);
            }
        }
        encontrados.sort(Comparator.comparing(Cliente::getNome));
        return encontrados;
    }
}
