package com.fcangussu.petecaentrosada.dominio;

public class PartidaNaoEncontradaException extends RuntimeException {

    public PartidaNaoEncontradaException(long id) {
        super("Partida não encontrada: " + id);
    }
}
