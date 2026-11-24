package br.com.bingo.model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private boolean possuiDivida;

    private static final String PADRAO_CPF = "[0-9]{11}";

    public Cliente(int id, String nome, String cpf, String telefone) {
        this.validarId(id);
        this.validarNome(nome);
        this.validarCpf(cpf);
        this.id = id;
        this.nome = nome.trim();
        this.cpf = cpf;
        this.telefone = telefone;
        this.possuiDivida = false; // Padrão
    }

    public Cliente(String id, String nome, boolean possuiDivida) {
        this.id = Integer.parseInt(id);
        this.nome = nome;
        this.possuiDivida = possuiDivida;
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id do cliente deve ser maior que zero.");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente nao pode ser vazio.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || !cpf.matches(PADRAO_CPF)) {
            throw new IllegalArgumentException("O CPF deve conter 11 digitos numericos.");
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public boolean isPossuiDivida() { return possuiDivida; }
    public void setPossuiDivida(boolean possuiDivida) { this.possuiDivida = possuiDivida; }
}