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

    private static final String PADRAO_CPF = "[0-9]{11}";

    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Cliente(int id, String nome, String cpf, String telefone) {
        validarId(id);
        validarNome(nome);
        validarCpf(cpf);
        this.id = id;
        this.nome = nome.trim();
        this.cpf = cpf;
        this.telefone = telefone;
    }

    private static void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id do cliente deve ser maior que zero.");
        }
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente e obrigatorio.");
        }
    }

    private static void validarCpf(String cpf) {
        if (cpf == null || !cpf.matches(PADRAO_CPF)) {
            throw new IllegalArgumentException("O CPF deve conter 11 digitos.");
        }
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

    @Override
    public String toString() {
        return "Cliente #" + id + " | Nome: " + nome + " | CPF: " + cpf + " | Telefone: " + telefone;
    }
}
