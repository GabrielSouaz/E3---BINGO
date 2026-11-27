package br.com.bingo.service;

import br.com.bingo.exception.RegistroNaoEncontradoException;
import br.com.bingo.model.Cliente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClienteService {
    
    private Map<Integer, Cliente> bancoDeDados = new HashMap<>();

   public void cadastrar(Cliente cliente) {
    validarCliente(cliente);
    validarIdDuplicado(cliente.getId());

    bancoDeDados.put(cliente.getId(), cliente);
}

    private void validarCliente(Cliente cliente) {
    if (cliente == null) {
        throw new IllegalArgumentException(
            "Cliente nao pode ser nulo."
        );
    }
}

    private void validarIdDuplicado(int id) {
    if (bancoDeDados.containsKey(id)) {
        throw new IllegalArgumentException(
            "Ja existe um cliente com esse id."
        );
    }
}

    public Optional<Cliente> buscarPorId(int id) {
        return Optional.ofNullable(bancoDeDados.get(id));
    }

    public List<Cliente> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(bancoDeDados.values()));
    }

    public void alterar(int id, String nome, String cpf, String telefone) {
        Cliente cliente = buscarParaAlteracao(id);
        validarCpfDisponivel(id, cpf);

        cliente.atualizarDados(nome, cpf, telefone);
    }

    private Cliente buscarParaAlteracao(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id do cliente deve ser maior que zero.");
        }
        Cliente cliente = bancoDeDados.get(id);
        if (cliente == null) {
            throw new RegistroNaoEncontradoException("Cliente com id " + id + " nao encontrado.");
        }
        return cliente;
    }

    private void validarCpfDisponivel(int idDoCliente, String cpf) {
        for (Cliente outro : bancoDeDados.values()) {
            boolean mesmoCpf = outro.getCpf() != null && outro.getCpf().equals(cpf);
            if (outro.getId() != idDoCliente && mesmoCpf) {
                throw new IllegalArgumentException("Ja existe outro cliente com esse CPF.");
            }
        }
    }

    public void excluir(int id) {
        Cliente cliente = buscarOuFalhar(id);
        if (cliente.isPossuiDivida()) {
            throw new IllegalStateException("Não é possível excluir um cliente com pendências financeiras.");
        }
        bancoDeDados.remove(id);
    }

    private Cliente buscarOuFalhar(int id) {
        Cliente cliente = bancoDeDados.get(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para exclusão.");
        }
        return cliente;
    }
}