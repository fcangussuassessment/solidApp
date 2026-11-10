package com.fcangussu.petecafominha;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entrada da API. Recebe e devolve Map&lt;String, Object&gt; "solto":
 * nada diz quais campos existem, e qualquer erro de digitação só aparece em produção.
 */
@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService service = new PartidaService(); // ❌ o próprio controller cria o service

    @PostMapping
    public Map<String, Object> criar(@RequestBody Map<String, String> corpo) {
        return service.criar(corpo.get("timeA"), corpo.get("timeB"), corpo.get("regra"));
    }

    @PostMapping("/{id}/pontos")
    public Map<String, Object> marcarPonto(@PathVariable long id, @RequestBody Map<String, String> corpo) {
        return service.registrarPonto(id, corpo.get("time"), corpo.get("tapa"));
    }

    @GetMapping("/{id}")
    public Map<String, Object> buscar(@PathVariable long id) {
        return service.buscar(id);
    }
}
