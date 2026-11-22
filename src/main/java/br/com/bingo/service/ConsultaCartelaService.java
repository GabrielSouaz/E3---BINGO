package br.com.bingo.service;

import java.util.ArrayList;
import java.util.List;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cartela;
import br.com.bingo.repository.CartelaRepositorio;

public class ConsultaCartelaService {

    private final CartelaRepositorio repositorio;

    public ConsultaCartelaService(CartelaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cartela buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id da cartela deve ser maior que zero.");
        }
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cartela com id " + id + " nao encontrada."));
    }

    public List<Cartela> listarPorCliente(int clienteId) {
        if (clienteId <= 0) {
            throw new IllegalArgumentException("O id do cliente deve ser maior que zero.");
        }
        List<Cartela> resultado = new ArrayList<>();
        for (Cartela cartela : repositorio.listarTodas()) {
            if (cartela.getCliente().getId() == clienteId) {
                resultado.add(cartela);
            }
        }
        return resultado;
    }
}
