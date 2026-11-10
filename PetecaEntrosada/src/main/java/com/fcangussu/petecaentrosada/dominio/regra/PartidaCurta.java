package com.fcangussu.petecaentrosada.dominio.regra;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fcangussu.petecaentrosada.dominio.Time;

/** Formato rápido deste app: vence quem chegar primeiro a 12 pontos. */
@Component
public class PartidaCurta implements RegraDePartida {

    private static final int PONTOS_PARA_VENCER = 12;

    @Override
    public String codigo() {
        return "CURTA";
    }

    @Override
    public String descricao() {
        return "Vence quem chegar primeiro a " + PONTOS_PARA_VENCER + " pontos";
    }

    @Override
    public Optional<Time> vencedor(int pontosA, int pontosB) {
        if (pontosA >= PONTOS_PARA_VENCER) {
            return Optional.of(Time.A);
        }
        if (pontosB >= PONTOS_PARA_VENCER) {
            return Optional.of(Time.B);
        }
        return Optional.empty();
    }
}
