package br.com.ybardockz.infraestructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import br.com.ybardockz.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Value("${algafood.storage.local.diretorio-foto}")
	private Path diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto foto) {
		try {
			Path arquivoPath = getArquivoPath(foto.getNomeArquivo());
			
			FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
				
		} catch (IOException e) {
			throw new StorageException("Não foi possivel armazenar arquivo", e);
		}
		
	}
	
	@Override
	public void remover(String nomeArquivo) {
		Path arquivoPath = getArquivoPath(nomeArquivo);
		
		try {
			Files.deleteIfExists(arquivoPath);
		} catch (IOException e) {
			throw new StorageException("Não foi possivel excluir o arquivo", e);
		}
		
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

}
