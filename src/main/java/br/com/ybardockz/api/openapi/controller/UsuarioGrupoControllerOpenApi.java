package br.com.ybardockz.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.GrupoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários")
public interface UsuarioGrupoControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),	
		
		@ApiResponse(responseCode = "400", description = "ID do usuário inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))	
		
	})
	@Operation(summary = "Lista os grupos de um usuário")
	public CollectionModel<GrupoModel> listar(@Parameter(required = true, example = "1") Long usuarioId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Grupo associado"),
		
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),	
		
		@ApiResponse(responseCode = "400", description = "ID do usuário ou grupo inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))	
		
	})
	@Operation(summary = "Associa um grupo a um usuário")
	public void associar(@Parameter(required = true, example = "1") Long usuarioId, 
			@Parameter(required = true, example = "1") Long grupoId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Grupo disassociado"),
		
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),	
		
		@ApiResponse(responseCode = "400", description = "ID do usuário ou grupo inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))	
		
	})
	@Operation(summary = "Disassocia um grupo de um usuário")
	public void disassociar(@Parameter(required = true, example = "1") Long usuarioId, 
			@Parameter(required = true, example = "1") Long grupoId);
	
}
