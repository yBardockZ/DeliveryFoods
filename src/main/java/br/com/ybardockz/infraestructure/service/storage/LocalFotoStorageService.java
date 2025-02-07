package br.com.ybardockz.infraestructure.service.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import br.com.ybardockz.core.storage.StorageProperties;
import br.com.ybardockz.domain.service.FotoStorageService;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
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
	
	@Override
	public InputStream recuperar(String nomeArquivo) {
		Path arquivoPath = getArquivoPath(nomeArquivo);
		File file = arquivoPath.toFile();
		
		if (!file.exists()) {
			return null;
		}
		
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new StorageException("Não foi possivel recuperar o arquivo", e);
		}
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFoto()
				.resolve(Path.of(nomeArquivo));
	}

}
