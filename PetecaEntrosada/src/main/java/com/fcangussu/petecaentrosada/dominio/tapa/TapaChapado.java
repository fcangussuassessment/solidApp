package com.fcangussu.petecaentrosada.dominio.tapa;

import org.springframework.stereotype.Component;

@Component
public class TapaChapado implements Tapa {

    @Override
    public String codigo() {
        return "CHAPADO";
    }

    @Override
    public String nome() {
        return "Tapa direto (chapado)";
    }

    @Override
    public String efeito() {
        return "Mão aberta e plana, sem girar o pulso: a peteca viaja reta e veloz.";
    }
}
