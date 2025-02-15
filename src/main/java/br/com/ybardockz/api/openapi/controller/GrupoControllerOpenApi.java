package br.com.ybardockz.api.openapi.controller;

import java.util.List;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.GrupoModel;
import br.com.ybardockz.api.model.input.GrupoInput;
import br.com.ybardockz.domain.model.Grupo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Grupos", description = "Gerencia os grupos")
public interface GrupoControllerOpenApi {

	@Operation(summary = "Lista os grupos")
	public List<GrupoModel> listar();
	
	@Operation(summary = "Busca um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	public GrupoModel buscarPorId(@Parameter(required = true, example = "1") 
		Long grupoId);
	
	@Operation(summary = "Cadastra um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Grupo cadastrado",
				content = @Content(schema = @Schema(implementation = GrupoModel.class)))
	})
	public GrupoModel adicionar(GrupoInput grupoInput);
	
	@Operation(summary = "Atualiza um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "200", description = "GrupoAtualizado",
		content = @Content(schema = @Schema(implementation = Grupo.class)))
	})
	public GrupoModel atualizar(@Parameter(required = true, example = "1") Long grupoId, GrupoInput grupoInput);
	
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Grupo excluido"),
		
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
		content = @Content(schema = @Schema(implementation = Grupo.class)))
	})
	@Operation(summary = "Deleta um grupo")
	public void deletar(@Parameter(required = true, example = "1") Long grupoId);

}
