package com.fcangussu.petecaentrosada.api;

import com.fcangussu.petecaentrosada.dominio.tapa.Tapa;

public record TapaResponse(String codigo, String nome, String efeito) {

    public static TapaResponse de(Tapa t) {
        return new TapaResponse(t.codigo(), t.nome(), t.efeito());
    }
}
