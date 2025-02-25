package br.com.ybardockz.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.v1.model.domain.UsuarioModel;
import br.com.ybardockz.api.v1.model.input.SenhaInput;
import br.com.ybardockz.api.v1.model.input.UsuarioComSenhaInput;
import br.com.ybardockz.api.v1.model.input.UsuarioInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Gerencia os usuários")
public interface UsuarioControllerOpenApi {
	
	@Operation(summary = "Lista os usuários")
	public CollectionModel<UsuarioModel> listar();
	
	@Operation(summary = "Busca um usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Id inválido.",
					content = @Content(schema = @Schema(implementation = Problema.class))),
			
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
			content = @Content(schema = @Schema(implementation = Problema.class)))
		})
	public UsuarioModel buscarPorId(@Parameter(description = "ID do usuário", required = true)
		Long usuarioId);
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuário cadastrado",
					content = @Content(schema = @Schema(implementation = UsuarioModel.class)))
	})
	@Operation(summary = "Registra um usuário")
	public UsuarioModel adicionar(UsuarioComSenhaInput usuarioComSenhaInput);
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado"),
			
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	@Operation(summary = "Atualiza um usuário")
	public UsuarioModel atualizar(UsuarioInput usuarioInput,
			@Parameter(description = "ID do usuário", required = true)
			Long usuarioId);
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado",
			content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Troca senha de um usuário")
	public void trocarSenha(
			@Parameter(description = "ID do usuário", required = true)
			Long usuarioId, 
			SenhaInput senhas);

}
