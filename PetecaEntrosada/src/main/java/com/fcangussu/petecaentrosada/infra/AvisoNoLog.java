package com.fcangussu.petecaentrosada.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fcangussu.petecaentrosada.aplicacao.AvisoDeResultado;
import com.fcangussu.petecaentrosada.dominio.Partida;

/**
 * Aviso padrão: registra o resultado no log.
 * Para avisar por e-mail ou WhatsApp, crie outra implementação de AvisoDeResultado.
 * O ArbitroDePartida não muda.
 */
@Component
public class AvisoNoLog implements AvisoDeResultado {

    private static final Logger LOG = LoggerFactory.getLogger(AvisoNoLog.class);

    @Override
    public void partidaEncerrada(Partida partida) {
        LOG.info("Fim de jogo! {} {} x {} {}. Vencedor: {}",
                partida.nomeTimeA(), partida.placar().pontosA(),
                partida.placar().pontosB(), partida.nomeTimeB(),
                partida.vencedor().map(partida::nomeDo).orElse("-"));
    }
}
