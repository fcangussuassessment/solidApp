package com.fcangussu.petecaentrosada.dominio;

/**
 * ✅ S: só conta pontos. Não sabe de tapas, regras, banco ou e-mail.
 */
public class Placar {

    private int pontosA;
    private int pontosB;

    public void marcar(Time time) {
        if (time == Time.A) {
            pontosA++;
        } else {
            pontosB++;
        }
    }

    public int pontosA() {
        return pontosA;
    }

    public int pontosB() {
        return pontosB;
    }
}
