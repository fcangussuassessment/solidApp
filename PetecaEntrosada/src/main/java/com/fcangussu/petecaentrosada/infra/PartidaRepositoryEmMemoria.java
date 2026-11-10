package com.fcangussu.petecaentrosada.infra;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.fcangussu.petecaentrosada.aplicacao.PartidaRepository;
import com.fcangussu.petecaentrosada.dominio.Partida;

/** Implementação simples para estudo. Trocar por JPA não afeta o resto do sistema. */
@Repository
public class PartidaRepositoryEmMemoria implements PartidaRepository {

    private final Map<Long, Partida> partidas = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong();

    @Override
    public long novoId() {
        return sequencia.incrementAndGet();
    }

    @Override
    public void salvar(Partida partida) {
        partidas.put(partida.id(), partida);
    }

    @Override
    public Optional<Partida> buscar(long id) {
        return Optional.ofNullable(partidas.get(id));
    }
}
