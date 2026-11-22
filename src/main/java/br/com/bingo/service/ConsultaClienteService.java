package br.com.bingo.service;

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
}
