package com.fcangussu.petecaentrosada.dominio;

public class PartidaEncerradaException extends RuntimeException {

    public PartidaEncerradaException(long id) {
        super("A partida " + id + " já foi encerrada");
    }
}
