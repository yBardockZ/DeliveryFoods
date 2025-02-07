package br.com.ybardockz.infraestructure.service.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.core.storage.StorageProperties;
import br.com.ybardockz.domain.service.FotoStorageService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private S3Client s3Client;
	
	@Autowired
	private S3Presigner s3Presigner;
	
	@Autowired
	private StorageProperties storageProperties;
	
	

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			GetObjectRequest getObjectRequest = GetObjectRequest.builder()
					.bucket(storageProperties.getS3()
							.getBucket())
					.key(getCaminho(nomeArquivo))
					.build();
			
			GetObjectPresignRequest getObjectPresigner =
					GetObjectPresignRequest.builder()
					.signatureDuration(Duration.of(10L, ChronoUnit.MINUTES))
					.getObjectRequest(getObjectRequest)
					.build();
			
			URL url = s3Presigner.presignGetObject(getObjectPresigner).url();
			
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
					.url(url.toString())
					.build();
			
			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possivel recuperar foto da AmazonS3", e);
		}
		
	}

	@Override
	public void armazenar(NovaFoto foto) {
		try {
			String caminhoArquivo = getCaminho(foto.getNomeArquivo());
			String nomeBucket = storageProperties.getS3().getBucket();
		    
		    String contentType = foto.getContentType();
		 
			
				s3Client.putObject(
			PutObjectRequest.builder()
				.bucket(nomeBucket)
				.key(caminhoArquivo)
				.contentType(contentType)
				.build(), 
				createRequestBody(foto.getInputStream())
			);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possivel enviar arquivos para AmazonS3 foto", e);
		} 
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
					.bucket(storageProperties.getS3().getBucket())
					.key(getCaminho(nomeArquivo))
					.build();
			
			s3Client.deleteObject(deleteRequest);
		} catch (RuntimeException e) {
			throw new StorageException("Não foi possivel deletar arquivo na AmazonS3", e);
		}
	}

	private String getCaminho(String nomeArquivo) {
		return String.format("%s/%s", 
				storageProperties.getS3().getDiretorio(),
				nomeArquivo);
	}
	
	private RequestBody createRequestBody(InputStream inputStream) throws IOException {
	    // Copia o InputStream para um array de bytes
	    byte[] bytes = toByteArray(inputStream);
	    long contentLength = bytes.length; // Obtém o tamanho
	    
	    // Cria um novo InputStream a partir dos bytes
	    InputStream newInputStream = new ByteArrayInputStream(bytes);

	    // Retorna o RequestBody
	    return RequestBody.fromInputStream(newInputStream, contentLength);
	}
	
	private byte[] toByteArray(InputStream inputStream) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    byte[] data = new byte[8192]; // Buffer de 8KB
	    int bytesRead;
	    while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
	        buffer.write(data, 0, bytesRead);
	    }
	    return buffer.toByteArray();
	}

}
