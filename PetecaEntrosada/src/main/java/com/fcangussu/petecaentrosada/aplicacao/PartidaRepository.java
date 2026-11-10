package com.fcangussu.petecaentrosada.aplicacao;

import java.util.Optional;

import com.fcangussu.petecaentrosada.dominio.Partida;

/**
 * ✅ D: a aplicação depende desta abstração, não de um banco específico.
 * Hoje é memória; amanhã pode ser JPA ou Redis sem mexer no ArbitroDePartida.
 */
public interface PartidaRepository {

    long novoId();

    void salvar(Partida partida);

    Optional<Partida> buscar(long id);
}
