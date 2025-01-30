package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.model.Pedido;
import jakarta.transaction.Transactional;

@Service
public class AlteracaoStatusPedidoService {
	
	@Autowired
	private EmissaoPedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		pedido.confirmar();
		
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		pedido.cancelar();
		
	}
	
	@Transactional
	public void confirmarEntrega(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		pedido.confirmarEntrega();
		
		
	}

}
