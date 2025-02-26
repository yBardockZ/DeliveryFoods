package br.com.ybardockz.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import br.com.ybardockz.domain.service.AlteracaoStatusPedidoService;

@RestController
@RequestMapping("/v1/pedido/{pedidoCodigo}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {
	
	@Autowired
	private AlteracaoStatusPedidoService alteracaoStatusPedidoService;
	
	@PutMapping("/confirmar")
	public ResponseEntity<Void> confirmar(@PathVariable String pedidoCodigo) {
		alteracaoStatusPedidoService.confirmar(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/confirmar-entrega")
	public ResponseEntity<Void> confirmarEntrega(@PathVariable String pedidoCodigo) {
		alteracaoStatusPedidoService.confirmarEntrega(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cancelar")
	public ResponseEntity<Void> cancelar(@PathVariable String pedidoCodigo) {
		alteracaoStatusPedidoService.cancelar(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}


}
