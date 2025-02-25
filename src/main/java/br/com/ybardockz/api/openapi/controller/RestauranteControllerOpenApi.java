package br.com.ybardockz.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.RestauranteApenasNomeModel;
import br.com.ybardockz.api.model.domain.RestauranteBasicoModel;
import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.api.model.input.RestauranteInput;
import br.com.ybardockz.api.openapi.model.RestauranteBasicoModelOpenApi;
import br.com.ybardockz.api.openapi.model.RestaurantesBasicoModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes", description = "Gerencia os restaurantes")
public interface RestauranteControllerOpenApi {
	
	@Parameters({
		
		@Parameter(
				name = "projecao",
				description = "Nome da projeção de restaurantes",
				in = ParameterIn.QUERY,
				schema = @Schema(allowableValues = {"apenas-nome"})
				)
		
	})
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = 
				@Schema(implementation = RestaurantesBasicoModelOpenApi.class)))
		
	})
	@Operation(summary = "Lista os restaurantes")
	public ResponseEntity<CollectionModel<RestauranteBasicoModel>> listar();
	
	ResponseEntity<CollectionModel<RestauranteApenasNomeModel>> listarApenasNome();
	
	@Operation(summary = "Busca um restaurante")
	@ApiResponses({
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
		content = @Content(schema = @Schema(implementation = RestauranteBasicoModelOpenApi.class)))
		
	})
	public RestauranteModel buscarPorId(@Parameter(required = true) Long restauranteId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "201", description = "Restaurante cadastrado",
				content = @Content(schema = @Schema(implementation = RestauranteBasicoModelOpenApi.class)))
		
	})
	@Operation(summary = "Cadastra um restaurante")
	RestauranteModel adicionar(@Parameter(required = true) RestauranteInput restauranteInput);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "200", description = "Restaurante Atualizado",
		content = @Content(schema = @Schema(implementation = RestauranteBasicoModelOpenApi.class)))
		
	})
	@Operation(summary = "Atualiza um restaurante")
	RestauranteModel atualizar(@Parameter(required = true) RestauranteInput restauranteInput, 
			@Parameter(required = true) Long restauranteId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurante ativado")
		
	})
	@Operation(summary = "Ativa um restaurante")
	ResponseEntity<Void> ativar(@Parameter(required = true) Long restauranteId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurantes não encontrados",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurantes ativados")
		
	})
	@Operation(summary = "Ativa multitplos restaurante")
	ResponseEntity<Void> ativarMultiplos(@Parameter(required = true) List<Long> restauranteIds);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurantes não encontrados",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurantes desativados")
		
	})
	@Operation(summary = "Desativa multiplos restaurante")
	ResponseEntity<Void> desativarMultiplos(@Parameter(required = true) List<Long> restauranteIds);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurante desativado")
		
	})
	@Operation(summary = "Desativa um restaurante")
	ResponseEntity<Void> inativar(@Parameter(required = true) Long restauranteId);

	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurante aberto")
		
	})
	@Operation(summary = "Abre um restaurante")
	ResponseEntity<Void> abrir(@Parameter(required = true) Long restauranteId);

	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurante fechado")
		
	})
	@Operation(summary = "Fecha um restaurante")
		ResponseEntity<Void> fechar(@Parameter(required = true) Long restauranteId);	
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
		content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Restaurante Removido")
		
	})
		@Operation(summary = "Remove um restaurante")
	void remover(@Parameter(required = true) Long restauranteId);

}
