package br.com.ybardockz.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ybardockz.core.email.EmailProperties.Impl;
import br.com.ybardockz.domain.service.EnvioEmailService;
import br.com.ybardockz.infraestructure.service.email.FakeEnvioEmailService;
import br.com.ybardockz.infraestructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties properties;
	
	@Bean
	EnvioEmailService envioEmailService() {
		if (properties.getImpl() == Impl.FAKE) {
			return new FakeEnvioEmailService();
		} 
		else {
			return new SmtpEnvioEmailService();
		}
	}
	
}
