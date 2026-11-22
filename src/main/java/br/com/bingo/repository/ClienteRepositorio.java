package br.com.bingo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.bingo.model.Cliente;

public class ClienteRepositorio {

    private final Map<Integer, Cliente> clientes = new LinkedHashMap<>();

    public void adicionar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao pode ser nulo.");
        }
        if (clientes.containsKey(cliente.getId())) {
            throw new IllegalArgumentException("Ja existe um cliente com o id " + cliente.getId() + ".");
        }
        clientes.put(cliente.getId(), cliente);
    }

    public List<Cliente> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(clientes.values()));
    }

    public Optional<Cliente> buscarPorId(int id) {
        return Optional.ofNullable(clientes.get(id));
    }
}
