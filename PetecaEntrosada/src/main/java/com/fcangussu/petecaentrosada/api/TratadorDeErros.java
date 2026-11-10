package com.fcangussu.petecaentrosada.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fcangussu.petecaentrosada.dominio.PartidaEncerradaException;
import com.fcangussu.petecaentrosada.dominio.PartidaNaoEncontradaException;

/** Erros de jogo viram respostas HTTP claras, em vez de um 500 genérico. */
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(PartidaNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> naoEncontrada(PartidaNaoEncontradaException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(PartidaEncerradaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> encerrada(PartidaEncerradaException e) {
        return Map.of("erro", e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> corpoInvalido(HttpMessageNotReadableException e) {
        return Map.of("erro", "Corpo da requisição inválido. Confira o JSON e se o time é A ou B.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalido(IllegalArgumentException e) {
        return Map.of("erro", e.getMessage());
    }
}
