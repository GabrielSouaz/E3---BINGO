package br.com.bingo.service;

import br.com.bingo.model.Cliente;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClienteService {
    private Map<String, Cliente> bancoDeDados = new HashMap<>();

    public void cadastrar(Cliente cliente) {
        bancoDeDados.put(cliente.getId(), cliente);
    }

    public Optional<Cliente> buscarPorId(String id) {
        return Optional.ofNullable(bancoDeDados.get(id));
    }

    public void excluir(String id) {
        Cliente cliente = bancoDeDados.get(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para exclusão.");
        }
        if (cliente.isPossuiDivida()) {
            throw new IllegalStateException("Não é possível excluir um cliente com pendências financeiras.");
        }
    }
}