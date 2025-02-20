package br.com.ybardockz.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.CozinhaModel;
import br.com.ybardockz.api.model.input.CozinhaInput;
import br.com.ybardockz.api.openapi.model.CozinhaModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cozinha", description = "Gerencia cozinhas")
public interface CozinhaControllerOpenApi {
	
	@Operation(summary = "Lista as cozinhas")
	@Parameters({
        @Parameter(
            name = "page",
            description = "Número da página (começa em 0)",
            in = ParameterIn.QUERY,
            example = "0"
        ),
        @Parameter(
            name = "size",
            description = "Quantidade de itens por página",
            in = ParameterIn.QUERY,
            example = "10"
        ),
        @Parameter(
            name = "sort",
            description = "Critérios de ordenação (ex: 'nome,asc')",
            in = ParameterIn.QUERY,
            example = "nome,asc"
        )
    })
	@ApiResponses({
		@ApiResponse(responseCode = "200", 
					description = "Consulta realizada",
					content = @Content(schema = @Schema(implementation = CozinhaModelOpenApi.class)))
	})
	PagedModel<CozinhaModel> listar(@Parameter(hidden = true) Pageable pageable);
	
	@ApiResponses({
		@ApiResponse(responseCode = "400",
					description = "ID inválido",
					content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404",
		description = "Cozinha não encontrada",
		content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Busca uma cozinha")
	CozinhaModel buscar(@Parameter(required = true, example = "1") Long id);

	@ApiResponses({
		@ApiResponse(responseCode = "201",
		description = "Cozinha cadastrada",
		content = @Content(schema = @Schema(implementation = CozinhaModel.class)))
	})
	@Operation(summary = "Cadastra uma cozinha")
	ResponseEntity<CozinhaModel> salvar(@Parameter(required = true) CozinhaInput cozinhaInput);
	
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					description = "Cozinha atualizada",
					content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404",
		description = "Cozinha não encontrada",
		content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Atualiza uma cozinha")
	CozinhaModel atualizar(@Parameter(required = true) CozinhaInput cozinhaInput, Long id);
	
	@ApiResponses({
		@ApiResponse(responseCode = "204",
					description = "Cozinha Removida"),
		
		@ApiResponse(responseCode = "404",
		description = "Cozinha não encontrada",
		content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Remove uma cozinha")
	void remover(@Parameter(required = true) Long id);

}

