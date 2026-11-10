package com.fcangussu.petecaentrosada.dominio.regra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fcangussu.petecaentrosada.dominio.Time;

/** Cada regra é testada sozinha, sem banco, sem HTTP, sem e-mail. */
class RegrasDePartidaTest {

    @Test
    void partidaCurtaTerminaEm12() {
        RegraDePartida curta = new PartidaCurta();
        assertTrue(curta.vencedor(11, 10).isEmpty());
        assertEquals(Optional.of(Time.A), curta.vencedor(12, 10));
        assertEquals(Optional.of(Time.B), curta.vencedor(3, 12));
    }

    @Test
    void partidaLongaExigeDoisDeVantagem() {
        RegraDePartida longa = new PartidaLonga();
        assertTrue(longa.vencedor(21, 20).isEmpty(), "21 x 20 ainda não acabou");
        assertTrue(longa.vencedor(22, 21).isEmpty(), "22 x 21 ainda não acabou");
        assertEquals(Optional.of(Time.A), longa.vencedor(23, 21));
        assertEquals(Optional.of(Time.B), longa.vencedor(15, 21));
    }
}
