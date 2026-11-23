package br.com.bingo.model;

public class Cliente {
    private String id;
    private String nome;
    private boolean possuiDivida;

    public Cliente(String id, String nome, boolean possuiDivida) {
        this.id = id;
        this.nome = nome;
        this.possuiDivida = possuiDivida;
    }
}