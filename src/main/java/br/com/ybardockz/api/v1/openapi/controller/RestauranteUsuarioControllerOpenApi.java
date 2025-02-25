package br.com.ybardockz.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.v1.model.domain.UsuarioModel;
import br.com.ybardockz.api.v1.openapi.model.UsuariosModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = UsuariosModelOpenApi.class))),
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Lista os usuários de um restaurante")
	CollectionModel<UsuarioModel> listar(@Parameter(required = true, example = "1") Long restauranteId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Usuário associado"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Associa um usuário a um restaurante")
	ResponseEntity<Void> associar(@Parameter(required = true, example = "1") Long usuarioId, 
			@Parameter(required = true, example = "1") Long restauranteId);

	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Usuário disassociado"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Disassocia um usuário de um restaurante")
	ResponseEntity<Void> disassociar(@Parameter(required = true, example = "1") Long usuarioId,
			@Parameter(required = true, example = "1") Long restauranteId);
	
}
