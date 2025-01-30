package br.com.ybardockz.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.domain.service.AlteracaoStatusPedidoService;

@RestController
@RequestMapping("/pedido/{pedidoId}")
public class FluxoPedidoController {
	
	@Autowired
	private AlteracaoStatusPedidoService alteracaoStatusPedidoService;
	
	@PutMapping("/confirmar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable Long pedidoId) {
		alteracaoStatusPedidoService.confirmar(pedidoId);
	}
	
	@PutMapping("/confirmar-entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarEntrega(@PathVariable Long pedidoId) {
		alteracaoStatusPedidoService.confirmarEntrega(pedidoId);
	}
	
	@PutMapping("/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable Long pedidoId) {
		alteracaoStatusPedidoService.cancelar(pedidoId);
	}


}
