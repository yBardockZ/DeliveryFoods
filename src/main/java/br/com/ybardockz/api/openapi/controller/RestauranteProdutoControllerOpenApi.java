package br.com.ybardockz.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.ProdutoModel;
import br.com.ybardockz.api.model.input.ProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos", description = "Gerencia os produtos")
public interface RestauranteProdutoControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Lista os produtos de um resestaurante")
	public CollectionModel<ProdutoModel> listar(@Parameter(required = true, example = "1") Long restauranteId, 
			Boolean incluirInativos);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID do Restaurante ou produto inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca um produto de um restaurante")
	public ProdutoModel buscarPorId(@Parameter(required = true, example = "1") Long produtoId, 
			@Parameter(required = true, example = "1") Long restauranteId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "201", description = "Produto cadastrado")
		
	})
	@Operation(summary = "Cadastra um produto em um restaurante")
	ProdutoModel adicionar(@Parameter(required = true, example = "1") Long restauranteId, 
			@Parameter(required = true) ProdutoInput produtoInput);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "200", description = "Produto atualizado")
		
	})
	@Operation(summary = "Atualiza um produto de um restaurante")
	ProdutoModel atualizar(@Parameter(required = true, example = "1") Long restauranteId, 
			@Parameter(required = true, example = "1") Long produtoId,
			@Parameter(required = true, example = "1") ProdutoInput produtoInput);

}
