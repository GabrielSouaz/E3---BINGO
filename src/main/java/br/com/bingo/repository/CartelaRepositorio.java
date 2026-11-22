package br.com.bingo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.bingo.model.Cartela;

public class CartelaRepositorio {

    private final Map<Integer, Cartela> cartelas = new LinkedHashMap<>();

    public void adicionar(Cartela cartela) {
        if (cartela == null) {
            throw new IllegalArgumentException("Cartela nao pode ser nula.");
        }
        if (cartelas.containsKey(cartela.getId())) {
            throw new IllegalArgumentException("Ja existe uma cartela com o id " + cartela.getId() + ".");
        }
        cartelas.put(cartela.getId(), cartela);
    }

    public List<Cartela> listarTodas() {
        return Collections.unmodifiableList(new ArrayList<>(cartelas.values()));
    }

    public Optional<Cartela> buscarPorId(int id) {
        return Optional.ofNullable(cartelas.get(id));
    }
}
