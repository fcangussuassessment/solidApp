package com.fcangussu.petecafominha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Os testes passam, mas repare no esforço:
 * - não dá para trocar o e-mail por um dublê, então o teste "espiona" o System.out;
 * - o "banco" é estático e compartilhado entre testes;
 * - tudo é Map&lt;String, Object&gt;, então cada leitura precisa de cast.
 */
class PartidaServiceTest {

    private final PartidaService service = new PartidaService();

    @Test
    void primeiroA12VenceNaPartidaCurta() {
        Map<String, Object> partida = service.criar("Fernando", "Rival", "CURTA");
        long id = (long) partida.get("id");

        for (int i = 0; i < 11; i++) {
            service.registrarPonto(id, "A", "CHAPADO");
        }
        assertNull(partida.get("vencedor"));

        service.registrarPonto(id, "A", "MUNHECA");
        assertEquals("Fernando", partida.get("vencedor"));
        assertEquals(12, (int) partida.get("pontosA"));
    }

    @Test
    void naoAceitaPontoDepoisDoFim() {
        long id = (long) service.criar("Fernando", "Rival", "CURTA").get("id");
        for (int i = 0; i < 12; i++) {
            service.registrarPonto(id, "B", "DEIXADINHA");
        }
        assertThrows(IllegalStateException.class, () -> service.registrarPonto(id, "A", "CHAPADO"));
    }

    @Test
    void tapaDesconhecidoEhRejeitado() {
        long id = (long) service.criar("Fernando", "Rival", "LONGA").get("id");
        assertThrows(IllegalArgumentException.class, () -> service.registrarPonto(id, "A", "DE_COSTAS"));
    }

    @Test
    void paraSaberSeOEmailFoiEnviadoPrecisoEspionarOConsole() {
        PrintStream original = System.out;
        ByteArrayOutputStream console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));
        try {
            long id = (long) service.criar("Fernando", "Rival", "CURTA").get("id");
            for (int i = 0; i < 12; i++) {
                service.registrarPonto(id, "A", "CHAPADO");
            }
        } finally {
            System.setOut(original);
        }
        assertTrue(console.toString().contains("[E-MAIL] Fim de jogo!"));
    }

    @Test
    void emailServiceQuebraAPromessaDaInterface() {
        ServicoDeNotificacao notificacao = new EmailService();
        // Quem confia na interface ServicoDeNotificacao recebe uma exceção (violação de Liskov).
        assertThrows(UnsupportedOperationException.class, () -> notificacao.enviarSms("oi"));
    }
}
