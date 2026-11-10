package com.fcangussu.petecafominha;

/**
 * Só sabe mandar e-mail, mas a interface gorda o obriga a "implementar" o resto.
 */
public class EmailService implements ServicoDeNotificacao {

    @Override
    public void enviarEmail(String mensagem) {
        System.out.println("[E-MAIL] " + mensagem); // simulação de envio
    }

    @Override
    public void enviarSms(String mensagem) {
        // ❌ Quebra a promessa da interface (viola o L): quem chamar via ServicoDeNotificacao explode.
        throw new UnsupportedOperationException("EmailService não envia SMS");
    }

    @Override
    public void enviarWhatsapp(String mensagem) {
        // ❌ Método vazio só para compilar (viola o I): falha em silêncio.
    }
}
