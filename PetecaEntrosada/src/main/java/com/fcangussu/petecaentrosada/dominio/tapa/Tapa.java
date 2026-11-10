package com.fcangussu.petecaentrosada.dominio.tapa;

/**
 * ✅ O: cada forma de golpear a peteca é uma classe que implementa esta interface.
 * Tapa novo = classe nova. Nenhum código existente precisa ser alterado.
 *
 * ✅ L: o contrato é simples e toda implementação deve cumpri-lo:
 * código único e não vazio, nome e efeito descritos. Sem exceções escondidas.
 */
public interface Tapa {

    /** Identificador usado na API, ex.: "CHAPADO". */
    String codigo();

    String nome();

    /** O que o tapa faz com a peteca. */
    String efeito();
}
