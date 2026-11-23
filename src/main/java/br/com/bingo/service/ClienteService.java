package br.com.bingo.service;

import br.com.bingo.model.Cliente;
import java.util.HashMap;
import java.util.Map;

public class ClienteService {
    private Map<String, Cliente> bancoDeDados = new HashMap<>();

    public void cadastrar(Cliente cliente) {
        bancoDeDados.put(cliente.getId(), cliente);
    }
}