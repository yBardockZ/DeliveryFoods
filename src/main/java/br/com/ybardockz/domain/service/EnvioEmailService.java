package br.com.ybardockz.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	public class Mensagem {
		
		private Set<String> destinatario;
		private String assunto;
		private String corpo;
		
	}
	
}
