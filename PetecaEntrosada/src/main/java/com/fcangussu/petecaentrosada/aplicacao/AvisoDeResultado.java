package com.fcangussu.petecaentrosada.aplicacao;

import com.fcangussu.petecaentrosada.dominio.Partida;

/**
 * ✅ I: uma interface pequena, com uma única intenção: avisar o fim da partida.
 * ✅ D: quem avisa (log, e-mail, WhatsApp) é decidido fora da regra de jogo.
 */
public interface AvisoDeResultado {

    void partidaEncerrada(Partida partida);
}
