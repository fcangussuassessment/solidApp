package com.fcangussu.petecaentrosada.aplicacao;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fcangussu.petecaentrosada.dominio.regra.RegraDePartida;

/** ✅ O: mesmo mecanismo do catálogo de tapas, para os formatos de partida. */
@Component
public class CatalogoDeRegras {

    private final Map<String, RegraDePartida> porCodigo;

    public CatalogoDeRegras(List<RegraDePartida> regras) {
        this.porCodigo = regras.stream().collect(Collectors.toMap(RegraDePartida::codigo, Function.identity()));
    }

    public RegraDePartida buscar(String codigo) {
        RegraDePartida regra = porCodigo.get(codigo);
        if (regra == null) {
            throw new IllegalArgumentException("Regra inválida: " + codigo + ". Disponíveis: " + porCodigo.keySet());
        }
        return regra;
    }
}
