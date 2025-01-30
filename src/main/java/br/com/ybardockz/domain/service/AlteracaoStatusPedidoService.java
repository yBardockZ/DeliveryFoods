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
	public void confirmar(String pedidoCodigo) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoCodigo);
		
		pedido.confirmar();
		
	}
	
	@Transactional
	public void cancelar(String pedidoCodigo) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoCodigo);
		
		pedido.cancelar();
		
	}
	
	@Transactional
	public void confirmarEntrega(String pedidoCodigo) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoCodigo);
		
		pedido.confirmarEntrega();
		
		
	}

}
