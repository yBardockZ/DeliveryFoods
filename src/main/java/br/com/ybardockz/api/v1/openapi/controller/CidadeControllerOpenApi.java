package br.com.ybardockz.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.v1.model.domain.CidadeModel;
import br.com.ybardockz.api.v1.model.input.CidadeInput;
import br.com.ybardockz.api.v1.openapi.model.CidadesModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cidades", description = "Gerencia as cidades")
public interface CidadeControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = CidadesModelOpenApi.class)))
		
	})
	@Operation(summary = "Lista as cidades")
	CollectionModel<CidadeModel> listar();

	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca uma cidade")
	CidadeModel buscarPorId(@Parameter(required = true, example = "1") Long id);

	@ApiResponses({
		
		@ApiResponse(responseCode = "201", description = "Cidade cadastrada"),
		
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Cadastra uma cidade")
	CidadeModel adicionar(CidadeInput cidadeInput);

	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Cidade atualizada"),
		
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Atualiza uma cidade")
	CidadeModel atualizar(CidadeInput cidadeInput, 
			@Parameter(required = true, example = "1") Long id);

	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Cidade removida"),
		
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Remove uma cidade")
	void remover(@Parameter(required = true, example = "1") Long id);
}
