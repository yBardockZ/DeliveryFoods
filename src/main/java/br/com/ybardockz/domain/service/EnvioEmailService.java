package br.com.ybardockz.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	@Getter
	@Setter
	@Builder
	public class Mensagem {
		
		@Singular(value = "destinatario")
		private Set<String> destinatario;
		
		@NonNull
		private String assunto;
		
		private String corpo;
		
		@Singular(value = "variavel")
		private Map<String, Object> variaveis;
		
	}
	
}
