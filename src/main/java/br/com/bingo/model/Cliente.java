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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public boolean isPossuiDivida() { return possuiDivida; }
    public void setPossuiDivida(boolean possuiDivida) { this.possuiDivida = possuiDivida; }
}