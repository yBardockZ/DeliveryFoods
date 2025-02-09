package br.com.ybardockz.infraestructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {
	
	@Override
	public void enviar(Mensagem mensagem) {
		log.info("Simulação de envio de e-mails: ");
		log.info("Enviando e-mail para: " + mensagem.getDestinatario());
		log.info(processarTemplate(mensagem));
		
	}

}
