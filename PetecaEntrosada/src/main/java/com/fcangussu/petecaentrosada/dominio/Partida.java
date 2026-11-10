package com.fcangussu.petecaentrosada.dominio;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.fcangussu.petecaentrosada.dominio.regra.RegraDePartida;
import com.fcangussu.petecaentrosada.dominio.tapa.Tapa;

/**
 * A partida conhece as regras do jogo, mas não sabe nada de HTTP, banco ou e-mail.
 * Ela delega: o Placar conta, a RegraDePartida decide o vencedor.
 */
public class Partida {

    private final long id;
    private final String nomeTimeA;
    private final String nomeTimeB;
    private final RegraDePartida regra;
    private final Placar placar = new Placar();
    private final Map<String, Integer> pontosPorTapa = new LinkedHashMap<>();
    private Time vencedor;

    public Partida(long id, String nomeTimeA, String nomeTimeB, RegraDePartida regra) {
        this.id = id;
        this.nomeTimeA = nomeTimeA;
        this.nomeTimeB = nomeTimeB;
        this.regra = regra;
    }

    public void registrarPonto(Time time, Tapa tapa) {
        if (encerrada()) {
            throw new PartidaEncerradaException(id);
        }
        placar.marcar(time);
        pontosPorTapa.merge(tapa.codigo(), 1, Integer::sum);
        vencedor = regra.vencedor(placar.pontosA(), placar.pontosB()).orElse(null);
    }

    public boolean encerrada() {
        return vencedor != null;
    }

    public Optional<Time> vencedor() {
        return Optional.ofNullable(vencedor);
    }

    public String nomeDo(Time time) {
        return time == Time.A ? nomeTimeA : nomeTimeB;
    }

    public long id() {
        return id;
    }

    public String nomeTimeA() {
        return nomeTimeA;
    }

    public String nomeTimeB() {
        return nomeTimeB;
    }

    public RegraDePartida regra() {
        return regra;
    }

    public Placar placar() {
        return placar;
    }

    public Map<String, Integer> pontosPorTapa() {
        return Collections.unmodifiableMap(pontosPorTapa);
    }
}
