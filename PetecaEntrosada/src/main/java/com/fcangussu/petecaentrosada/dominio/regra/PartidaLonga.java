package com.fcangussu.petecaentrosada.dominio.regra;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fcangussu.petecaentrosada.dominio.Time;

/** Formato longo deste app: 21 pontos, com pelo menos 2 de vantagem. */
@Component
public class PartidaLonga implements RegraDePartida {

    private static final int PONTOS_PARA_VENCER = 21;
    private static final int VANTAGEM_MINIMA = 2;

    @Override
    public String codigo() {
        return "LONGA";
    }

    @Override
    public String descricao() {
        return PONTOS_PARA_VENCER + " pontos, com " + VANTAGEM_MINIMA + " de vantagem";
    }

    @Override
    public Optional<Time> vencedor(int pontosA, int pontosB) {
        if (pontosA >= PONTOS_PARA_VENCER && pontosA - pontosB >= VANTAGEM_MINIMA) {
            return Optional.of(Time.A);
        }
        if (pontosB >= PONTOS_PARA_VENCER && pontosB - pontosA >= VANTAGEM_MINIMA) {
            return Optional.of(Time.B);
        }
        return Optional.empty();
    }
}
