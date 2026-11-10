# ❌ PetecaFominha: placar de peteca SEM SOLID

O **fominha** é o jogador que quer pegar todas as bolas, inclusive as do parceiro. Até funciona,
mas desorganiza o jogo e basta uma jogada diferente para tudo desandar.

Aqui o fominha é a classe `PartidaService`: ela cria a partida, valida o tapa, conta os pontos,
guarda estatísticas, decide o vencedor, "salva no banco" e manda o e-mail.

## O que observar no código

Os problemas estão marcados com `❌` nos comentários:

- `PartidaService`: tudo num método só, `if/else` de tapas e regras, números mágicos, `new EmailService()` e estado estático.
- `ServicoDeNotificacao`: interface "faz-tudo" (e-mail, SMS e WhatsApp).
- `EmailService`: lança exceção em `enviarSms()` e deixa `enviarWhatsapp()` vazio.
- `PartidaController`: cria o próprio service com `new` e usa `Map<String, Object>` como contrato.
- `PartidaServiceTest`: para verificar o e-mail, o teste precisa capturar o `System.out`.

## Documentação da API

Contrato OpenAPI 3.0 em `src/main/resources/static/openapi.yaml`, exibido no Swagger UI:
http://localhost:8080/swagger-ui.html. Repare que o contrato só consegue documentar `Map` solto e erro 500.

## Rodar

```bash
mvn spring-boot:run   # http://localhost:8080
mvn test
```

Compare com o **PetecaEntrosada** e veja o `README.md` da pasta `solidApp`.
