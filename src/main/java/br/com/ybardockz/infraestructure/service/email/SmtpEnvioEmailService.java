package br.com.ybardockz.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.ybardockz.core.email.EmailProperties;
import br.com.ybardockz.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class SmtpEnvioEmailService implements EnvioEmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freeMarkerConfig;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			
			MimeMessage mimeMessage = criarMimeMessage(mensagem);
			
			System.out.println("enviando email...");
			mailSender.send(mimeMessage);
			System.out.println("E-mail enviado com sucesso");
			
		} catch (Exception e) {
			System.out.println("Erro ao enviar o e-mail: " + e.getMessage());
			throw new EmailException("Não foi possivel enviar e-mail", e);
		}
	}
	
	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		String corpo = processarTemplate(mensagem);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
			
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
		mimeMessageHelper.setFrom(emailProperties.getRemetente());
		mimeMessageHelper.setSubject(mensagem.getAssunto());
		mimeMessageHelper.setTo(mensagem.getDestinatario().toArray(new String[0]));
		mimeMessageHelper.setText(corpo, true);
			
		return mimeMessage;
		
	}
	
	protected String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possivel montar o template do e-mail", e);
		}
	}
}
