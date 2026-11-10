package com.fcangussu.petecaentrosada.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fcangussu.petecaentrosada.aplicacao.ArbitroDePartida;
import com.fcangussu.petecaentrosada.aplicacao.CatalogoDeTapas;

/** ✅ S: só traduz HTTP ⇄ aplicação. Nenhuma regra de jogo aqui. */
@RestController
public class PartidaController {

    private final ArbitroDePartida arbitro;
    private final CatalogoDeTapas tapas;

    public PartidaController(ArbitroDePartida arbitro, CatalogoDeTapas tapas) {
        this.arbitro = arbitro;
        this.tapas = tapas;
    }

    @PostMapping("/partidas")
    @ResponseStatus(HttpStatus.CREATED)
    public PartidaResponse criar(@RequestBody NovaPartidaRequest req) {
        return PartidaResponse.de(arbitro.criar(req.timeA(), req.timeB(), req.regra()));
    }

    @PostMapping("/partidas/{id}/pontos")
    public PartidaResponse marcarPonto(@PathVariable long id, @RequestBody PontoRequest req) {
        return PartidaResponse.de(arbitro.marcarPonto(id, req.time(), req.tapa()));
    }

    @GetMapping("/partidas/{id}")
    public PartidaResponse buscar(@PathVariable long id) {
        return PartidaResponse.de(arbitro.buscar(id));
    }

    @GetMapping("/tapas")
    public List<TapaResponse> listarTapas() {
        return tapas.todos().stream().map(TapaResponse::de).toList();
    }
}
