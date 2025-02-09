package br.com.ybardockz.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import br.com.ybardockz.core.email.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class SandBoxEmailService extends SmtpEnvioEmailService {
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		MimeMessage mimeMessage = super.criarMimeMessage(mensagem);
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
		mimeMessageHelper.setTo(emailProperties.getSandboxDestinatario());
		
		return mimeMessage;
	}
}
