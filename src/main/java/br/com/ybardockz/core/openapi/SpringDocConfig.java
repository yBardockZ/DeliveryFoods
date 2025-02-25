package br.com.ybardockz.core.openapi;

import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ybardockz.api.exceptionhandler.Problema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
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
				.addOpenApiCustomizer(adicionarSchemasCustomizados())
				.addOpenApiCustomizer(substituirPageablePorModeloPersonalizado())
				.addOpenApiCustomizer(substituirLinksPorModeloPersonalizado())
				.addOpenApiCustomizer(substituirCollectionModelPorModeloPersonalizado())
				.addOpenApiCustomizer(globalGetResponse())
				.addOpenApiCustomizer(globalPostResponse())
				.addOpenApiCustomizer(globalPutResponse())
				.addOpenApiCustomizer(globalDeleteResponse())
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
	OpenApiCustomizer globalGetResponse() {
		return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
				if (pathItem.getGet() != null) {
					ApiResponses responses = pathItem.getGet().getResponses();
	
					responses.addApiResponse("500", new ApiResponse()
							.description("Erro interno do servidor")
							.content(new Content()
									.addMediaType("application/json", new MediaType()
											.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
					responses.addApiResponse("406", new ApiResponse()
							.description("Recurso não possui formata" + "ção que poderia ser aceita pelo consumidor"));
				}
			});	
	}
	
	@Bean
	OpenApiCustomizer globalPostResponse() {
		return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
			if (pathItem.getPost() != null) {
				ApiResponses responses = pathItem.getPost().getResponses();

				responses.addApiResponse("400", new ApiResponse()
						.description("Dados da requisição inválidos")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
				
				responses.addApiResponse("406", new ApiResponse()
						.description("Recurso não possui formatação que poderia ser aceita pelo consumidor"));
				
				responses.addApiResponse("500", new ApiResponse()
						.description("Erro interno do servidor")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
				
				responses.addApiResponse("415", new ApiResponse()
						.description("Requisição recusada porque o corpo está em um formato não suportado")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
			
			}

		});
	}
	
	@Bean
	OpenApiCustomizer globalPutResponse() {
		return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
			if (pathItem.getPut() != null) {
				ApiResponses responses = pathItem.getPut().getResponses();

				responses.addApiResponse("400", new ApiResponse()
						.description("Dados da requisição inválidos")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
				
				responses.addApiResponse("406", new ApiResponse()
						.description("Recurso não possui formatação que poderia ser aceita pelo consumidor"));
				
				responses.addApiResponse("500", new ApiResponse()
						.description("Erro interno do servidor")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
				
				responses.addApiResponse("415", new ApiResponse()
						.description("Requisição recusada porque o " + "corpo está em um formato não suportado")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
			
			}

		});
	}
	
	@Bean
	OpenApiCustomizer globalDeleteResponse() {
		return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
			if (pathItem.getDelete() != null) {
				ApiResponses responses = pathItem.getDelete().getResponses();
				
				responses.addApiResponse("400", new ApiResponse().description("Requisição inválida")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
				
				responses.addApiResponse("500", new ApiResponse().description("Erro interno do servidor")
						.content(new Content().addMediaType("application/json", new MediaType()
								.schema(new Schema<>().$ref("#/components/schemas/Problema")))));
			}
		});
	}
	
	@Bean
	OpenApiCustomizer adicionarSchemasCustomizados() {
	    return openApi -> {
	        // Criando schema de ObjetoProblema
	        Schema<?> objetoProblemaSchema = new Schema<>()
	            .type("object")
	            .addProperty("name", new Schema<>().type("string").example("email"))
	            .addProperty("userMessage", new Schema<>().type("string").example("O e-mail do usuário é obrigatório"))
	            .description("Representação de um campo com erro");

	        // Criando a lista de objetosProblema dentro de Problema
	        @SuppressWarnings("unchecked")
			Schema<Problema.Object> listaObjetosProblemaSchema = new Schema<>()
	            .type("array")
	            .items(new Schema<>().$ref("#/components/schemas/ObjetoProblema"));

	        // Criando schema de Problema
	        Schema<?> problemaSchema = new Schema<>()
	            .type("object")
	            .addProperty("status", new Schema<>().type("integer").example(400))
	            .addProperty("timestamp", new Schema<>().type("string").example("2025-02-14T10:15:30Z"))
	            .addProperty("title", new Schema<>().type("string").example("Requisição inválida"))
	            .addProperty("detail", new Schema<>().type("string").example("Um ou mais campos estão inválidos."))
	            .addProperty("userMessage", new Schema<>().type("string").example("Preencha corretamente e tente novamente."))
	            .addProperty("objects", listaObjetosProblemaSchema)
	            .description("Representação de um problema");

	        // Adicionando schemas ao OpenAPI
	        openApi.getComponents().addSchemas("Problema", problemaSchema);
	        openApi.getComponents().addSchemas("ObjetoProblema", objetoProblemaSchema);
	    };
	}
	
	@SuppressWarnings("unchecked")
	@Bean
	OpenApiCustomizer substituirPageablePorModeloPersonalizado() {
		return openApi -> {
			
			openApi.getComponents().getSchemas().remove("Pageable");
			openApi.getComponents().getSchemas().remove("PageableObject");
			openApi.getComponents().getSchemas().remove("SortObject");
			
			openApi.getComponents().addSchemas("Pageable", new Schema<>()
	                .type("object")
	                .addProperty("page", new Schema<>().type("integer").example(0))
	                .addProperty("size", new Schema<>().type("integer").example(10))
	                .addProperty("sort", new Schema<>()
	                    .type("array")
	                    .items(new Schema<>().type("string"))
	                    .example(List.of("nome,asc"))));
		};
		
	}

    @Bean
    OpenApiCustomizer substituirLinksPorModeloPersonalizado() {
        return openApi -> {
            // Remover o schema "Links" gerado automaticamente
            openApi.getComponents().getSchemas().remove("Links");
            openApi.getComponents().getSchemas().remove("Link");

            // Instanciando o schema "LinkModel" conforme sua estrutura desejada
            openApi.getComponents().addSchemas("LinkModel", new Schema<>()
                    .type("object")
                    .addProperty("href", new Schema<>().type("string").example("http://localhost:8080/recurso"))
                    .addProperty("templated", new Schema<>().type("boolean").example(false)));

            // Instanciando o schema "LinksModelOpenApi" com a referência para "LinkModel"
            openApi.getComponents().addSchemas("LinksModelOpenApi", new Schema<>()
                    .type("object")
                    .addProperty("ref", new Schema<>().$ref("#/components/schemas/LinkModel")));
            
            openApi.getComponents().addSchemas("Links", new Schema<>()
                    .$ref("#/components/schemas/LinksModelOpenApi"));
            
            openApi.getComponents().addSchemas("Link", new Schema<>()
                    .$ref("#/components/schemas/LinkModel"));
        };
    }
    

	@Bean
    OpenApiCustomizer substituirCollectionModelPorModeloPersonalizado() {
        return openApi -> {
            // Remover o schema CollectionModel<CidadeModel> (se já existir com outro nome)
            openApi.getComponents().getSchemas().remove("CollectionModelCidadeModel");
            openApi.getComponents().getSchemas().remove("CollectionModelEstadoModel");
            openApi.getComponents().getSchemas().remove("CollectionModelGrupoModel");
            openApi.getComponents().getSchemas().remove("CollectionModelProdutoModel");

        };
    }

}