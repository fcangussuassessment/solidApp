package com.fcangussu.petecaentrosada.api;

import java.util.Map;

import com.fcangussu.petecaentrosada.dominio.Partida;

/** Resposta da API: campos explícitos em vez de Map<String, Object>. */
public record PartidaResponse(long id, String timeA, String timeB, String regra,
                              int pontosA, int pontosB, String vencedor,
                              Map<String, Integer> pontosPorTapa) {

    public static PartidaResponse de(Partida p) {
        return new PartidaResponse(p.id(), p.nomeTimeA(), p.nomeTimeB(), p.regra().descricao(),
                p.placar().pontosA(), p.placar().pontosB(),
                p.vencedor().map(p::nomeDo).orElse(null),
                p.pontosPorTapa());
    }
}
