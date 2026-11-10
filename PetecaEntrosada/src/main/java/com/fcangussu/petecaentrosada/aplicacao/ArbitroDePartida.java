package com.fcangussu.petecaentrosada.aplicacao;

import org.springframework.stereotype.Service;

import com.fcangussu.petecaentrosada.dominio.Partida;
import com.fcangussu.petecaentrosada.dominio.PartidaNaoEncontradaException;
import com.fcangussu.petecaentrosada.dominio.Time;
import com.fcangussu.petecaentrosada.dominio.tapa.Tapa;

/**
 * O "árbitro" coordena a jogada, mas não faz o trabalho dos outros:
 * - o catálogo encontra o tapa e a regra;
 * - a partida aplica a regra do jogo;
 * - o repositório guarda;
 * - o aviso comunica o resultado.
 *
 * ✅ S: só orquestra o caso de uso.
 * ✅ D: tudo chega pelo construtor como abstração; o Spring injeta as implementações.
 */
@Service
public class ArbitroDePartida {

    private final PartidaRepository repositorio;
    private final CatalogoDeTapas tapas;
    private final CatalogoDeRegras regras;
    private final AvisoDeResultado aviso;

    public ArbitroDePartida(PartidaRepository repositorio, CatalogoDeTapas tapas,
                            CatalogoDeRegras regras, AvisoDeResultado aviso) {
        this.repositorio = repositorio;
        this.tapas = tapas;
        this.regras = regras;
        this.aviso = aviso;
    }

    public Partida criar(String nomeTimeA, String nomeTimeB, String codigoRegra) {
        Partida partida = new Partida(repositorio.novoId(), nomeTimeA, nomeTimeB, regras.buscar(codigoRegra));
        repositorio.salvar(partida);
        return partida;
    }

    public Partida marcarPonto(long idPartida, Time time, String codigoTapa) {
        Partida partida = buscar(idPartida);
        Tapa tapa = tapas.buscar(codigoTapa);

        partida.registrarPonto(time, tapa);
        repositorio.salvar(partida);

        if (partida.encerrada()) {
            aviso.partidaEncerrada(partida);
        }
        return partida;
    }

    public Partida buscar(long idPartida) {
        return repositorio.buscar(idPartida).orElseThrow(() -> new PartidaNaoEncontradaException(idPartida));
    }
}
