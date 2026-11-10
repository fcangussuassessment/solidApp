package com.fcangussu.petecaentrosada.dominio.regra;

import java.util.Optional;

import com.fcangussu.petecaentrosada.dominio.Time;

/**
 * ✅ O: o formato da partida é trocável. Formato novo = classe nova.
 * ✅ I: interface pequena, só o que a partida precisa saber.
 */
public interface RegraDePartida {

    /** Identificador usado na API, ex.: "CURTA". */
    String codigo();

    String descricao();

    /** Diz quem venceu com este placar, ou vazio se a partida continua. */
    Optional<Time> vencedor(int pontosA, int pontosB);
}
