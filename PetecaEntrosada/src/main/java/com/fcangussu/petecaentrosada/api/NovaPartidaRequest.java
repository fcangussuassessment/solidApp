package com.fcangussu.petecaentrosada.api;

/** Corpo do POST /partidas. Ex.: {"timeA":"Fernando","timeB":"Rival","regra":"CURTA"} */
public record NovaPartidaRequest(String timeA, String timeB, String regra) {
}
