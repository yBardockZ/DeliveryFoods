package br.com.ybardockz.domain.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.enums.StatusPedido;
import jakarta.transaction.Transactional;

@Service
public class AlteracaoStatusPedidoService {
	
	@Autowired
	private EmissaoPedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		if (pedido.getStatus() == StatusPedido.CRIADO) {
			pedido.setStatus(StatusPedido.CONFIRMADO);
			pedido.setDataConfirmacao(Instant.now());
		}
		else {
			throw new NegocioException("Não foi possivel confirmar o pedido: " + pedidoId + 
					" pois o status dele é: " + pedido.getStatus());
			
		}
		
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		if (pedido.getStatus() == StatusPedido.CONFIRMADO) {
			pedido.setStatus(StatusPedido.CANCELADO);
			pedido.setDataCancelamento(Instant.now());
		}
		else {
			throw new NegocioException("Não foi possível cancelar o pedido: " + pedidoId +
					" pois o status dele é: " + pedido.getStatus());
		}
		
	}
	
	@Transactional
	public void confirmarEntrega(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		if (pedido.getStatus() == StatusPedido.CONFIRMADO) {
			pedido.setStatus(StatusPedido.ENTREGUE);
			pedido.setDataEntrega(Instant.now());
		} else {
			throw new NegocioException("Não foi possivel confimar a entrega do pedido: " + pedidoId +
					" pois o status dele é " + pedido.getStatus());
		}
		
		
	}

}
