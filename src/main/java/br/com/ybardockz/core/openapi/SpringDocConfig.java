package br.com.ybardockz.core.openapi;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SpringDocConfig {
	
	@Bean
	GroupedOpenApi apiGrupo1() {
		return GroupedOpenApi.builder()
				.group("publico")
				.packagesToScan("br.com.ybardockz.api")
				.pathsToExclude("/teste/**")
				.addOpenApiCustomizer(globalResponse())
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
	
	@Bean
	OpenApiCustomizer globalResponse() {
		return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
			if (pathItem.getGet() != null) {
				ApiResponses responses = pathItem.getGet().getResponses();
				
				responses.addApiResponse("500", new ApiResponse().description("Erro interno do servidor"));
				responses.addApiResponse("406", new ApiResponse().description("Recurso não possui formata"
						+ "ção que poderia ser aceita pelo consumidor"));
				responses.addApiResponse("200", new ApiResponse().description("Consulta realizada com sucesso"));
			}
		});
	}
	
	

}
