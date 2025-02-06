package br.com.ybardockz.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar (NovaFoto foto);
	
	void remover (String nomeArquivo);
	
	default void substituir(NovaFoto novaFoto, String nomeFotoAntiga) {
		armazenar(novaFoto);
		
		if (nomeFotoAntiga != null) {
			remover(nomeFotoAntiga);
		}
	}
	
	default String gerarNome(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
	
	@Getter
	@Builder
	public class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
		
	}
}
