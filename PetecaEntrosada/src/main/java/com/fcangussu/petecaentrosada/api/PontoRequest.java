package com.fcangussu.petecaentrosada.api;

import com.fcangussu.petecaentrosada.dominio.Time;

/** Corpo do POST /partidas/{id}/pontos. Ex.: {"time":"A","tapa":"CHAPADO"} */
public record PontoRequest(Time time, String tapa) {
}
