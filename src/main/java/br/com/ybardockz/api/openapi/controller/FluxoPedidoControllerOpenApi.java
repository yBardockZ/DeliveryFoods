package br.com.ybardockz.api.openapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

import br.com.ybardockz.api.exceptionhandler.Problema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Pedido confirmado"),
		
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Confirmar pedido")
	@PutMapping("/pedido/{pedidoCodigo}/confirmar")
	public ResponseEntity<Void> confirmar(String pedidoCodigo);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Pedido entregue"),
		
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Confirmar entrega")
	@PutMapping("/pedido/{pedidoCodigo}/confirmar-entrega")
	public ResponseEntity<Void> confirmarEntrega(String pedidoCodigo);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Pedido cancelado"),
		
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Cancelar pedido")
	@PutMapping("/pedido/{pedidoCodigo}/cancelar")
	public ResponseEntity<Void> cancelar(String pedidoCodigo);
	
}
