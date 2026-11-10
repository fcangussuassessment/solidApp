# ✅ PetecaEntrosada: placar de peteca COM SOLID

Na **dupla entrosada**, cada jogador cobre a sua parte da quadra e confia no parceiro.
Aqui, cada classe tem um papel claro e as peças se encaixam por interfaces.

## Estrutura

```
dominio/          regras do jogo, sem Spring, HTTP ou banco
  Partida, Placar, Time
  tapa/           Tapa + TapaChapado, TapaComMunheca, TapaAmortecido
  regra/          RegraDePartida + PartidaCurta, PartidaLonga
aplicacao/        ArbitroDePartida (caso de uso), catálogos e as interfaces
                  PartidaRepository e AvisoDeResultado
infra/            PartidaRepositoryEmMemoria, AvisoNoLog
api/              PartidaController, records de request/response, TratadorDeErros
```

## Os princípios no código

Marcados com `✅` nos comentários:

- **S**: `Placar` só conta, `RegraDePartida` só decide, `ArbitroDePartida` só coordena e `PartidaController` só traduz HTTP.
- **O**: um tapa novo é só uma classe com `@Component`; o `CatalogoDeTapas` recebe todos do Spring. Veja `NovoTapaSemMexerEmNadaTest`.
- **L**: todos os tapas passam no mesmo teste de contrato (`ContratoDosTapasTest`).
- **I**: as interfaces são pequenas e focadas.
- **D**: o `ArbitroDePartida` recebe abstrações pelo construtor. No teste, entra um dublê simples (`ArbitroDePartidaTest`).

## Documentação da API

Contrato OpenAPI 3.0 em `src/main/resources/static/openapi.yaml`, exibido no Swagger UI:
http://localhost:8081/swagger-ui.html. O contrato documenta records explícitos e os erros 400, 404 e 409.

## Rodar

```bash
mvn spring-boot:run   # http://localhost:8081
mvn test
```

Exemplos de chamadas da API estão no `README.md` da pasta `solidApp`.
