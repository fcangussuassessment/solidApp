package com.fcangussu.petecaentrosada.dominio.tapa;

import org.springframework.stereotype.Component;

@Component
public class TapaAmortecido implements Tapa {

    @Override
    public String codigo() {
        return "DEIXADINHA";
    }

    @Override
    public String nome() {
        return "Tapa amortecido (deixadinha)";
    }

    @Override
    public String efeito() {
        return "Mão e pulso relaxados: a peteca perde velocidade e cai junto à rede.";
    }
}
