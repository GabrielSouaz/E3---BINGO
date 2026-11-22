package br.com.bingo.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cartela;
import br.com.bingo.repository.CartelaRepositorio;

public class ConsultaCartelaService {

    private final CartelaRepositorio repositorio;

    public ConsultaCartelaService(CartelaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cartela buscarPorId(int id) {
        validarPositivo(id, "O id da cartela deve ser maior que zero.");
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cartela com id " + id + " nao encontrada."));
    }

    public List<Cartela> listarPorCliente(int clienteId) {
        validarPositivo(clienteId, "O id do cliente deve ser maior que zero.");
        return filtrar(cartela -> cartela.getCliente().getId() == clienteId);
    }

    public List<Cartela> listarPorRodada(int numeroRodada) {
        validarPositivo(numeroRodada, "O numero da rodada deve ser maior que zero.");
        return filtrar(cartela -> cartela.getNumeroRodada() == numeroRodada);
    }

    public double calcularTotalVendido() {
        return somarValores(repositorio.listarTodas());
    }

    public double calcularTotalVendidoPorRodada(int numeroRodada) {
        return somarValores(listarPorRodada(numeroRodada));
    }

    private List<Cartela> filtrar(Predicate<Cartela> criterio) {
        return repositorio.listarTodas().stream()
                .filter(criterio)
                .collect(Collectors.toList());
    }

    private static double somarValores(List<Cartela> cartelas) {
        return cartelas.stream().mapToDouble(Cartela::getValor).sum();
    }

    private static void validarPositivo(int valor, String mensagem) {
        if (valor <= 0) {
            throw new IllegalArgumentException(mensagem);
        }
    }
}
