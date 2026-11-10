package com.fcangussu.petecaentrosada.dominio.tapa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * ✅ L (Liskov): o MESMO teste vale para qualquer Tapa.
 * Se um tapa novo não passar aqui, ele não pode substituir os outros.
 */
class ContratoDosTapasTest {

    private final List<Tapa> todos = List.of(new TapaChapado(), new TapaComMunheca(), new TapaAmortecido());

    @Test
    void todoTapaCumpreOContrato() {
        for (Tapa tapa : todos) {
            assertFalse(tapa.codigo() == null || tapa.codigo().isBlank(), "código vazio em " + tapa);
            assertFalse(tapa.nome() == null || tapa.nome().isBlank(), "nome vazio em " + tapa);
            assertFalse(tapa.efeito() == null || tapa.efeito().isBlank(), "efeito vazio em " + tapa);
        }
    }

    @Test
    void codigosSaoUnicos() {
        Set<String> codigos = todos.stream().map(Tapa::codigo).collect(Collectors.toSet());
        assertEquals(todos.size(), codigos.size());
    }
}
