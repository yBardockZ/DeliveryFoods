package br.com.ybardockz.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.PedidoModel;
import br.com.ybardockz.api.model.domain.PedidoResumoModel;
import br.com.ybardockz.api.model.input.PedidoInput;
import br.com.ybardockz.api.openapi.model.PedidoModelOpenApi;
import br.com.ybardockz.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface PedidoControllerOpenApi {
	
	@Parameters({
		
		@Parameter(
				name = "size",
				description = "Quantidade de itens por página",
				in = ParameterIn.QUERY,
				example = "10"),
		
		@Parameter(
				name = "page",
				description = "Número da página (começa em 0)",
				in = ParameterIn.QUERY,
				example = "0"),
		
		@Parameter(
				name = "sort",
				description = "Critérios de ordenação",
				in = ParameterIn.QUERY,
				example = "nome,asc"
				),
		
		@Parameter(
				name = "clienteId",
				description = "Id do cliente",
				in = ParameterIn.QUERY,
				example = "1"
				),
		
		@Parameter(
				name = "restauranteId",
				description = "Id do restaurante",
				in = ParameterIn.QUERY,
				example = "1"
				),
		
		@Parameter(
				name = "dataCriacaoInicio",
				description = "Data inicial",
				in = ParameterIn.QUERY,
				example = "2025-02-17T21:37:02.221Z"
				),
		
		@Parameter(
				name = "DataCriacaoFinal",
				description = "Data final",
				in = ParameterIn.QUERY,
				example = "2025-02-18T21:37:02.221Z"
				)
		
		
	})
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = PedidoModelOpenApi.class)))
		
	})
	@Operation(summary = "Pesquisa pedidos")
	PagedModel<PedidoResumoModel> pesquisar(@Parameter(hidden = true) PedidoFilter filtro, 
			@Parameter(hidden = true) Pageable pageable);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = PedidoModel.class))),
		
		@ApiResponse(responseCode = "400", description = "Código inválido",
		content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca um pedido")
	PedidoModel buscarPorId(@Parameter(required = true) String pedidoCodigo);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "201", description = "Pedido cadastrado",
				content = @Content(schema = @Schema(implementation = PedidoModel.class))),
		
	})
	@Operation(summary = "Cadastra um pedido")
	PedidoModel salvar(PedidoInput pedidoInput);
		
	
}
