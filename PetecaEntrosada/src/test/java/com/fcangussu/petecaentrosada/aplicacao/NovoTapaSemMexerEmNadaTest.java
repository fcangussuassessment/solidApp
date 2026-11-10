package com.fcangussu.petecaentrosada.aplicacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fcangussu.petecaentrosada.dominio.Partida;
import com.fcangussu.petecaentrosada.dominio.Time;
import com.fcangussu.petecaentrosada.dominio.regra.PartidaCurta;
import com.fcangussu.petecaentrosada.dominio.tapa.Tapa;
import com.fcangussu.petecaentrosada.dominio.tapa.TapaChapado;
import com.fcangussu.petecaentrosada.infra.PartidaRepositoryEmMemoria;

/**
 * ✅ O (Aberto/Fechado): um tapa novo entra no jogo sem alterar
 * nenhuma linha de Partida, Placar, ArbitroDePartida ou dos outros tapas.
 *
 * No PetecaFominha, a mesma mudança exige editar o if/else do PartidaService.
 */
class NovoTapaSemMexerEmNadaTest {

    /** Tapa inventado só para o teste. Na aplicação real, bastaria anotar com @Component. */
    static class TapaDeCostas implements Tapa {
        @Override
        public String codigo() {
            return "DE_COSTAS";
        }

        @Override
        public String nome() {
            return "Tapa de costas";
        }

        @Override
        public String efeito() {
            return "Golpe sem olhar, de costas para a rede.";
        }
    }

    @Test
    void tapaNovoFuncionaSemAlterarOResto() {
        ArbitroDePartida arbitro = new ArbitroDePartida(
                new PartidaRepositoryEmMemoria(),
                new CatalogoDeTapas(List.of(new TapaChapado(), new TapaDeCostas())),
                new CatalogoDeRegras(List.of(new PartidaCurta())),
                partida -> { });

        long id = arbitro.criar("Fernando", "Rival", "CURTA").id();
        Partida partida = arbitro.marcarPonto(id, Time.A, "DE_COSTAS");

        assertEquals(1, partida.placar().pontosA());
        assertEquals(1, partida.pontosPorTapa().get("DE_COSTAS"));
    }
}
