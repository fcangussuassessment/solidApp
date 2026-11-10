package com.fcangussu.petecaentrosada.aplicacao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fcangussu.petecaentrosada.dominio.tapa.Tapa;

/**
 * ✅ O: o Spring entrega aqui TODAS as classes que implementam Tapa.
 * Criou um tapa novo com @Component? Ele aparece no catálogo sozinho.
 */
@Component
public class CatalogoDeTapas {

    private final Map<String, Tapa> porCodigo;

    public CatalogoDeTapas(List<Tapa> tapas) {
        this.porCodigo = tapas.stream().collect(Collectors.toMap(Tapa::codigo, Function.identity()));
    }

    public Tapa buscar(String codigo) {
        Tapa tapa = porCodigo.get(codigo);
        if (tapa == null) {
            throw new IllegalArgumentException("Tapa desconhecido: " + codigo + ". Disponíveis: " + porCodigo.keySet());
        }
        return tapa;
    }

    public Collection<Tapa> todos() {
        return porCodigo.values();
    }
}
