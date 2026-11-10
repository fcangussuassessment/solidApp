# solidApp: SOLID explicado com peteca 🪶

Dois projetos **Java 21 + Spring Boot** com **a mesma API** de placar de partida de peteca,
construídos de formas opostas:

| | ❌ **PetecaFominha** (sem SOLID) | ✅ **PetecaEntrosada** (com SOLID) |
|---|---|---|
| **A metáfora** | O fominha quer pegar todas as bolas e atrapalha o jogo | Na dupla entrosada, cada um cobre a sua parte da quadra |
| **Organização** | Um `PartidaService` que pontua, valida, decide o vencedor, "salva" e avisa | `Placar`, `Partida`, `ArbitroDePartida`, `PartidaRepository`, `AvisoDeResultado`: cada um com um papel |
| **Tipos de tapa** | `if/else` com textos: `"CHAPADO"`, `"MUNHECA"`, `"DEIXADINHA"` | Interface `Tapa`: `TapaChapado`, `TapaComMunheca`, `TapaAmortecido` |
| **Novo tapa** | Editar o `if/else` no meio do código que já funciona | Criar uma classe nova com `@Component`. Nada mais muda |
| **Regra da partida** | Números 12, 21 e 2 fixos dentro do método | Interface `RegraDePartida`: `PartidaCurta`, `PartidaLonga` |
| **Aviso do resultado** | `new EmailService()` dentro da regra de jogo | `AvisoDeResultado` injetado pelo Spring |
| **Dados da API** | `Map<String, Object>` solto | `record`s do Java 21 com campos explícitos |
| **Erros** | Exceção genérica, HTTP 500 | 400, 404 e 409 com mensagem clara |
| **Testes** | Precisam "espionar" o console para saber se o e-mail saiu | Dublês simples no lugar das dependências |

## Onde cada princípio aparece

| Princípio | ❌ Violação no PetecaFominha | ✅ Solução no PetecaEntrosada |
|---|---|---|
| **S**: Responsabilidade Única | `PartidaService.registrarPonto()` faz tudo | `Placar` só conta, `RegraDePartida` só decide o vencedor, `ArbitroDePartida` só coordena |
| **O**: Aberto/Fechado | `if/else` de tapas e de regras | Tapa ou regra nova = classe nova (veja `NovoTapaSemMexerEmNadaTest`) |
| **L**: Substituição de Liskov | `EmailService.enviarSms()` lança exceção | Todo `Tapa` passa no mesmo teste de contrato (`ContratoDosTapasTest`) |
| **I**: Segregação de Interfaces | `ServicoDeNotificacao` obriga e-mail, SMS e WhatsApp | Interfaces pequenas: `Tapa`, `RegraDePartida`, `AvisoDeResultado` |
| **D**: Inversão de Dependência | `new PartidaService()` e `new EmailService()` no código | Dependências chegam pelo construtor; o Spring injeta |

## Como rodar

Requisitos: Java 21 e Maven.

```bash
cd PetecaFominha   && mvn spring-boot:run   # porta 8080
cd PetecaEntrosada && mvn spring-boot:run   # porta 8081
mvn test                                    # em cada projeto
```

## Documentação da API (OpenAPI 3.0 + Swagger UI)

Cada projeto tem o contrato escrito à mão em `src/main/resources/static/openapi.yaml`,
exibido pelo Swagger UI (springdoc). Com o projeto rodando:

| | Swagger UI | Contrato |
|---|---|---|
| PetecaFominha | http://localhost:8080/swagger-ui.html | http://localhost:8080/openapi.yaml |
| PetecaEntrosada | http://localhost:8081/swagger-ui.html | http://localhost:8081/openapi.yaml |

Abra os dois lado a lado: o contrato do Fominha só tem `Map` solto e erro 500,
e o do Entrosada tem campos explícitos e os erros 400, 404 e 409.

## Experimente a API (troque a porta para comparar)

```bash
# criar partida (regras: CURTA = 12 pontos, LONGA = 21 com 2 de vantagem)
curl -X POST localhost:8081/partidas -H "Content-Type: application/json" \
     -d '{"timeA":"Fernando","timeB":"Rival","regra":"CURTA"}'

# marcar ponto (tapas: CHAPADO, MUNHECA, DEIXADINHA)
curl -X POST localhost:8081/partidas/1/pontos -H "Content-Type: application/json" \
     -d '{"time":"A","tapa":"CHAPADO"}'

# consultar placar
curl localhost:8081/partidas/1

# só no PetecaEntrosada: catálogo de tapas
curl localhost:8081/tapas
```

> As regras CURTA e LONGA são formatos deste projeto de estudo, não as regras oficiais da peteca.

## O desafio para sentir a diferença

Adicione um **tapa novo** e troque o **aviso por e-mail por um aviso por WhatsApp** nos dois projetos.
No PetecaFominha, você vai editar o `PartidaService` em vários pontos.
No PetecaEntrosada, você só cria duas classes novas.

---
Projeto de estudo de Fernando Cangussu.
