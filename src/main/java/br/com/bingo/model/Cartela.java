package br.com.bingo.model;

public class Cartela {

    private int id;
    private Cliente cliente;
    private double valor;
    private int numeroRodada;

    public Cartela(int id, Cliente cliente, double valor, int numeroRodada) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id da cartela deve ser maior que zero.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("A cartela precisa estar vinculada a um cliente.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da cartela deve ser maior que zero.");
        }
        if (numeroRodada <= 0) {
            throw new IllegalArgumentException("O numero da rodada deve ser maior que zero.");
        }
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.numeroRodada = numeroRodada;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getValor() {
        return valor;
    }

    public int getNumeroRodada() {
        return numeroRodada;
    }

    @Override
    public String toString() {
        return String.format("Cartela #%d | Cliente: %s | Valor: R$ %.2f | Rodada: %d",
                id, cliente.getNome(), valor, numeroRodada);
    }
}
