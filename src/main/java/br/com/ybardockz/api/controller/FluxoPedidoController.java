package br.com.ybardockz.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.openapi.controller.FluxoPedidoControllerOpenApi;
import br.com.ybardockz.domain.service.AlteracaoStatusPedidoService;

@RestController
@RequestMapping("/pedido/{pedidoCodigo}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {
	
	@Autowired
	private AlteracaoStatusPedidoService alteracaoStatusPedidoService;
	
	@PutMapping("/confirmar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String pedidoCodigo) {
		alteracaoStatusPedidoService.confirmar(pedidoCodigo);
	}
	
	@PutMapping("/confirmar-entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarEntrega(@PathVariable String pedidoCodigo) {
		alteracaoStatusPedidoService.confirmarEntrega(pedidoCodigo);
	}
	
	@PutMapping("/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String pedidoCodigo) {
		alteracaoStatusPedidoService.cancelar(pedidoCodigo);
	}


}
