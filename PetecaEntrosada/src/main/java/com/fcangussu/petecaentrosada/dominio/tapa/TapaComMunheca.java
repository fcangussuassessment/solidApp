package com.fcangussu.petecaentrosada.dominio.tapa;

import org.springframework.stereotype.Component;

@Component
public class TapaComMunheca implements Tapa {

    @Override
    public String codigo() {
        return "MUNHECA";
    }

    @Override
    public String nome() {
        return "Tapa com efeito (munheca)";
    }

    @Override
    public String efeito() {
        return "Torção rápida do pulso no impacto: a peteca faz uma parábola inesperada.";
    }
}
