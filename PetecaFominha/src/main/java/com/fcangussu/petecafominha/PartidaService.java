package com.fcangussu.petecafominha;

import java.util.HashMap;
import java.util.Map;

/**
 * O FOMINHA: a classe que quer pegar todas as bolas.
 *
 * Ela cria a partida, valida o tapa, conta os pontos, guarda as estatísticas,
 * decide o vencedor, "salva no banco" e ainda manda o e-mail.
 *
 * Funciona, mas qualquer mudança (um tapa novo, uma regra nova, trocar e-mail por WhatsApp)
 * obriga a mexer AQUI, no meio de tudo o que já funciona.
 */
public class PartidaService {

    // ❌ "Banco de dados" estático: o estado vaza entre testes e entre instâncias.
    private static final Map<Long, Map<String, Object>> PARTIDAS = new HashMap<>();
    private static long proximoId = 1;

    // ❌ Dependência concreta criada aqui dentro (viola o D): impossível trocar ou simular.
    private final EmailService email = new EmailService();

    public Map<String, Object> criar(String timeA, String timeB, String regra) {
        // ❌ Regras conhecidas fixas no código (viola o O): regra nova = editar este if.
        if (!"CURTA".equals(regra) && !"LONGA".equals(regra)) {
            throw new IllegalArgumentException("Regra inválida: " + regra);
        }
        Map<String, Object> partida = new HashMap<>();
        partida.put("id", proximoId);
        partida.put("timeA", timeA);
        partida.put("timeB", timeB);
        partida.put("regra", regra);
        partida.put("pontosA", 0);
        partida.put("pontosB", 0);
        partida.put("vencedor", null);
        partida.put("pontosPorTapa", new HashMap<String, Integer>());
        PARTIDAS.put(proximoId, partida);
        proximoId++;
        return partida;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> registrarPonto(long id, String time, String tapa) {
        Map<String, Object> partida = buscar(id);

        if (partida.get("vencedor") != null) {
            throw new IllegalStateException("Partida encerrada");
        }

        // ❌ Cada tapa é um texto num if/else (viola o O): tapa novo = editar este bloco.
        if ("CHAPADO".equals(tapa)) {
            System.out.println("Tapa chapado: mão aberta, forte e reto.");
        } else if ("MUNHECA".equals(tapa)) {
            System.out.println("Tapa com munheca: torção do pulso, trajetória inesperada.");
        } else if ("DEIXADINHA".equals(tapa)) {
            System.out.println("Tapa amortecido: mão relaxada, cai junto à rede.");
        } else {
            throw new IllegalArgumentException("Tapa desconhecido: " + tapa);
        }

        // ❌ Placar, estatística, regra de vitória e aviso, tudo no mesmo método (viola o S).
        if ("A".equals(time)) {
            partida.put("pontosA", (int) partida.get("pontosA") + 1);
        } else if ("B".equals(time)) {
            partida.put("pontosB", (int) partida.get("pontosB") + 1);
        } else {
            throw new IllegalArgumentException("Time inválido: " + time);
        }

        Map<String, Integer> pontosPorTapa = (Map<String, Integer>) partida.get("pontosPorTapa");
        pontosPorTapa.merge(tapa, 1, Integer::sum);

        int a = (int) partida.get("pontosA");
        int b = (int) partida.get("pontosB");

        // ❌ Números mágicos espalhados: 12, 21, 2...
        if ("CURTA".equals(partida.get("regra"))) {
            if (a >= 12) {
                partida.put("vencedor", partida.get("timeA"));
            } else if (b >= 12) {
                partida.put("vencedor", partida.get("timeB"));
            }
        } else {
            if (a >= 21 && a - b >= 2) {
                partida.put("vencedor", partida.get("timeA"));
            } else if (b >= 21 && b - a >= 2) {
                partida.put("vencedor", partida.get("timeB"));
            }
        }

        if (partida.get("vencedor") != null) {
            email.enviarEmail("Fim de jogo! " + partida.get("timeA") + " " + a + " x " + b + " "
                    + partida.get("timeB") + ". Vencedor: " + partida.get("vencedor"));
        }

        return partida;
    }

    public Map<String, Object> buscar(long id) {
        Map<String, Object> partida = PARTIDAS.get(id);
        if (partida == null) {
            // ❌ Sem tratamento de erro na API: o cliente recebe um 500 genérico.
            throw new RuntimeException("Partida não encontrada: " + id);
        }
        return partida;
    }
}
