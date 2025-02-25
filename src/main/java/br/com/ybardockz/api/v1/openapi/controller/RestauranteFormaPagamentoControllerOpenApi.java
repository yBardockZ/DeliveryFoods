package br.com.ybardockz.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.v1.model.domain.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Lista as formas de pagamento do restaurante")
	public CollectionModel<FormaPagamentoModel> listar(@Parameter(required = true, example = "1") Long restauranteId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Forma de pagamento desassociada"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Dissasocia uma forma de pagamento de um restaurante")
	public ResponseEntity<Void> dissasociar(@Parameter(required = true, example = "1") Long restauranteId, 
			@Parameter(required = true) Long formaPagamentoId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Forma de pagamento associada"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Associa forma de pagamento com um restaurante")
	public ResponseEntity<Void> associar(@Parameter(required = true, example = "1") Long restauranteId, 
			@Parameter(required = true) Long formaPagamentoId);

}
