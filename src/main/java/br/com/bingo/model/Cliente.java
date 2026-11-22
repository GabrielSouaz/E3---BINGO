package br.com.bingo.model;

public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Cliente(int id, String nome, String cpf, String telefone) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id do cliente deve ser maior que zero.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente e obrigatorio.");
        }
        if (cpf == null || !cpf.matches("[0-9]{11}")) {
            throw new IllegalArgumentException("O CPF deve conter 11 digitos.");
        }
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }
}
