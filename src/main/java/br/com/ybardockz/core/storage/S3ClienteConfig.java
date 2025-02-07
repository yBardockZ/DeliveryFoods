package br.com.ybardockz.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3ClienteConfig {
	
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
	
}
