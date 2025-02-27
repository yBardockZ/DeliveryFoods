package br.com.ybardockz.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.v1.model.domain.PermissaoModel;
import br.com.ybardockz.api.v1.openapi.model.PermissoesModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = PermissoesModelOpenApi.class))),
		
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
				
		@ApiResponse(responseCode = "400", description = "ID de grupo inválido",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Lista as permissões de um grupo")
	public CollectionModel<PermissaoModel> listar(@Parameter(required = true, example = "1") Long grupoId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
				
		@ApiResponse(responseCode = "400", description = "ID de grupo ou permissão inválido",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca uma permissão de um grupo")
	public PermissaoModel buscarPorId(@Parameter(required = true, example = "1") Long grupoId, 
			@Parameter(required = true, example = "1") Long permissaoId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Permissão associada"),
		
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
				
		@ApiResponse(responseCode = "400", description = "ID de grupo ou permissao inválido",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Associa uma permissão a um grupo")
	public ResponseEntity<Void> associar(Long grupoId, Long permissaoId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Permissão disassociada"),
		
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
				
		@ApiResponse(responseCode = "400", description = "ID de grupo ou permissao inválido",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Disassocia uma permissão de um grupo")
	public ResponseEntity<Void> disassociar(Long grupoId, Long permissaoId);

}
