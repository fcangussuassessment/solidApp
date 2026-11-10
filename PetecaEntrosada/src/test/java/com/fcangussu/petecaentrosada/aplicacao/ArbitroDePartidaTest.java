package com.fcangussu.petecaentrosada.aplicacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fcangussu.petecaentrosada.dominio.Partida;
import com.fcangussu.petecaentrosada.dominio.PartidaEncerradaException;
import com.fcangussu.petecaentrosada.dominio.PartidaNaoEncontradaException;
import com.fcangussu.petecaentrosada.dominio.Time;
import com.fcangussu.petecaentrosada.dominio.regra.PartidaCurta;
import com.fcangussu.petecaentrosada.dominio.regra.PartidaLonga;
import com.fcangussu.petecaentrosada.dominio.tapa.TapaAmortecido;
import com.fcangussu.petecaentrosada.dominio.tapa.TapaChapado;
import com.fcangussu.petecaentrosada.dominio.tapa.TapaComMunheca;
import com.fcangussu.petecaentrosada.infra.PartidaRepositoryEmMemoria;

/**
 * ✅ D na prática: como o árbitro recebe abstrações, o teste passa um aviso "dublê"
 * que só anota as chamadas. Sem console, sem e-mail de verdade, sem Mockito.
 */
class ArbitroDePartidaTest {

    /** Dublê de AvisoDeResultado: guarda as partidas avisadas. */
    static class AvisoQueAnota implements AvisoDeResultado {
        final List<Partida> avisadas = new ArrayList<>();

        @Override
        public void partidaEncerrada(Partida partida) {
            avisadas.add(partida);
        }
    }

    private final AvisoQueAnota aviso = new AvisoQueAnota();

    private final ArbitroDePartida arbitro = new ArbitroDePartida(
            new PartidaRepositoryEmMemoria(),
            new CatalogoDeTapas(List.of(new TapaChapado(), new TapaComMunheca(), new TapaAmortecido())),
            new CatalogoDeRegras(List.of(new PartidaCurta(), new PartidaLonga())),
            aviso);

    @Test
    void primeiroA12VenceEOAvisoEhDisparadoUmaVez() {
        long id = arbitro.criar("Fernando", "Rival", "CURTA").id();

        for (int i = 0; i < 11; i++) {
            arbitro.marcarPonto(id, Time.A, "CHAPADO");
        }
        assertTrue(aviso.avisadas.isEmpty());

        Partida partida = arbitro.marcarPonto(id, Time.A, "MUNHECA");

        assertEquals("Fernando", partida.vencedor().map(partida::nomeDo).orElseThrow());
        assertEquals(1, aviso.avisadas.size());
        assertEquals(11, partida.pontosPorTapa().get("CHAPADO"));
        assertEquals(1, partida.pontosPorTapa().get("MUNHECA"));
    }

    @Test
    void naoAceitaPontoDepoisDoFim() {
        long id = arbitro.criar("Fernando", "Rival", "CURTA").id();
        for (int i = 0; i < 12; i++) {
            arbitro.marcarPonto(id, Time.B, "DEIXADINHA");
        }
        assertThrows(PartidaEncerradaException.class, () -> arbitro.marcarPonto(id, Time.A, "CHAPADO"));
    }

    @Test
    void tapaDesconhecidoERegraInvalidaSaoRejeitados() {
        long id = arbitro.criar("Fernando", "Rival", "LONGA").id();
        assertThrows(IllegalArgumentException.class, () -> arbitro.marcarPonto(id, Time.A, "DE_COSTAS"));
        assertThrows(IllegalArgumentException.class, () -> arbitro.criar("Fernando", "Rival", "RELAMPAGO"));
    }

    @Test
    void partidaInexistente() {
        assertThrows(PartidaNaoEncontradaException.class, () -> arbitro.buscar(999));
    }
}
