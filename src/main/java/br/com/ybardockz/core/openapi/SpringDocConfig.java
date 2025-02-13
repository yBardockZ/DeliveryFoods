package br.com.ybardockz.core.openapi;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {
	
	@Bean
	GroupedOpenApi apiGrupo1() {
		return GroupedOpenApi.builder()
				.group("publico")
				.packagesToScan("br.com.ybardockz.api")
				.pathsToExclude("/teste/**")
				.build();
	}
	
	@Bean
	OpenAPI apiInfo() {
		return new OpenAPI()
				.info(new Info()
						.title("Algafood API")
						.description("Api aberta para clientes e restaurantes")
						.version("1")
						.contact(new Contact()
								.email("thalles_leopoldino@outlook.com")
								.name("Thalles")
								.url("www.ybardockz.com")));
		
	}

}
