package br.com.ybardockz.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.v1.model.domain.CozinhaModel;
import br.com.ybardockz.api.v2.model.CozinhaModelV2;
import br.com.ybardockz.api.v2.model.input.CozinhaInputV2;
import br.com.ybardockz.api.v2.openapi.model.CozinhasModelOpenApiV2;
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
public interface CozinhaControllerOpenApiV2 {
	
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
					content = @Content(schema = @Schema(implementation = CozinhasModelOpenApiV2.class)))
	})
	PagedModel<CozinhaModelV2> listar(@Parameter(hidden = true) Pageable pageable);
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", 
				description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = CozinhaModel.class))),
		
		@ApiResponse(responseCode = "400",
					description = "ID inválido",
					content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404",
		description = "Cozinha não encontrada",
		content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Busca uma cozinha")
	CozinhaModelV2 buscar(@Parameter(required = true, example = "1") Long id);

	@ApiResponses({
		@ApiResponse(responseCode = "201",
		description = "Cozinha cadastrada",
		content = @Content(schema = @Schema(implementation = CozinhaModelV2.class)))
	})
	@Operation(summary = "Cadastra uma cozinha")
	ResponseEntity<CozinhaModelV2> salvar(@Parameter(required = true) CozinhaInputV2 cozinhaInput);
	
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					description = "Cozinha atualizada",
					content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404",
		description = "Cozinha não encontrada",
		content = @Content(schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Atualiza uma cozinha")
	CozinhaModelV2 atualizar(@Parameter(required = true) CozinhaInputV2 cozinhaInput, Long id);
	
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

