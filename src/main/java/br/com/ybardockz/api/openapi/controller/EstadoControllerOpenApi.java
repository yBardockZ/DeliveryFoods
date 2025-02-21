package br.com.ybardockz.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.EstadoModel;
import br.com.ybardockz.api.model.input.EstadoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estados", description = "Gerencia os estados")
public interface EstadoControllerOpenApi {

	@Operation(summary = "Lista os estados")
	ResponseEntity<CollectionModel<EstadoModel>> listar();
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Estado não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca um estado")
	EstadoModel buscarPorId(@Parameter(required = true) Long id);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "201", description = "Estado cadastrado"),
		
		
	})
	@Operation(summary = "Cadastra um estado")
	EstadoModel adicionar(@Parameter(required = true) EstadoInput estadoInput);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "201", description = "Estado atualizado"),
		
		
	})
	@Operation(summary = "Atualiza um estado")
	EstadoModel atualizar(EstadoInput estadoInput, 
			@Parameter(required = true) Long id);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Estado removido"),
		
		@ApiResponse(responseCode = "404", description = "Estado não encontrado",
		content = @Content(schema = @Schema(implementation = Problema.class))),

		@ApiResponse(responseCode = "400", description = "ID inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
		
	})
	@Operation(summary = "Remove um estado")
	ResponseEntity<?> remover(@Parameter(required = true) Long id);

}
