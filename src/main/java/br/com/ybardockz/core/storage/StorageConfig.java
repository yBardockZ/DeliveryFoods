package br.com.ybardockz.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ybardockz.core.storage.StorageProperties.TipoStorage;
import br.com.ybardockz.domain.service.FotoStorageService;
import br.com.ybardockz.infraestructure.service.storage.LocalFotoStorageService;
import br.com.ybardockz.infraestructure.service.storage.S3FotoStorageService;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;

    @Bean
    S3Client s3Client() {
		Region region = Region.of(
				storageProperties.getS3().getRegiao());
	
		var credentials = StaticCredentialsProvider.create(
				AwsBasicCredentials.create(storageProperties.getS3().getIdChaveAcesso(),
						storageProperties.getS3().getIdChaveAcessoSecreta()));
		
		return S3Client.builder()
				.region(region)
				.credentialsProvider(credentials)
				.build();
	}
    
    @Bean
    S3Presigner s3Presigner() {
    	Region region = Region.of(
				storageProperties.getS3().getRegiao());
    	
    	var credentials = StaticCredentialsProvider.create(
    			AwsBasicCredentials.create(storageProperties.getS3().getIdChaveAcesso(), 
    					storageProperties.getS3().getIdChaveAcessoSecreta()));
    	
    	S3Presigner s3Presigner = S3Presigner.builder()
    			.region(region)
    			.credentialsProvider(credentials)
    			.build();
    	
    	return s3Presigner;
    }
    
    @Bean
    FotoStorageService fotoStorageService() {
    	if (storageProperties.getTipoStorage() == TipoStorage.S3) {
    		return new S3FotoStorageService();
    	}
    	else {
    		return new LocalFotoStorageService();
    	}
    }
	
}
