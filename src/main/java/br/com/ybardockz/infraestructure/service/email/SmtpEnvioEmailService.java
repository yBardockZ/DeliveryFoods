package br.com.ybardockz.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.ybardockz.core.email.EmailProperties;
import br.com.ybardockz.domain.service.EnvioEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			
			mimeMessageHelper.setFrom(emailProperties.getRemetente());
			mimeMessageHelper.setSubject(mensagem.getAssunto());
			mimeMessageHelper.setText(mensagem.getCorpo(), true);
			mimeMessageHelper.setTo(mensagem.getDestinatario().toArray(new String[0]));
			
			System.out.println("enviando email...");
			mailSender.send(mimeMessage);
			System.out.println("E-mail enviado com sucesso");
			
		} catch (MessagingException e) {
			System.out.println("Erro ao envia o e-mail: " + e.getMessage());
			throw new EmailException("Não foi possivel enviar e-mail", e);
		}
		
		
	}
}
