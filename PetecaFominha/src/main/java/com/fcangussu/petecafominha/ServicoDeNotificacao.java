package com.fcangussu.petecafominha;

/**
 * ❌ Interface "faz-tudo" (viola o I): quem implementa é obrigado a saber
 * mandar e-mail, SMS e WhatsApp, mesmo que só use um deles.
 */
public interface ServicoDeNotificacao {

    void enviarEmail(String mensagem);

    void enviarSms(String mensagem);

    void enviarWhatsapp(String mensagem);
}
