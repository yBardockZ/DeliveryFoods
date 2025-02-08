package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.service.EnvioEmailService.Mensagem;
import jakarta.transaction.Transactional;

@Service
public class AlteracaoStatusPedidoService {
	
	@Autowired
	private EmissaoPedidoService pedidoService;
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@Transactional
	public void confirmar(String pedidoCodigo) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoCodigo);
		
		pedido.confirmar();
		
		envioEmail.enviar(Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build()
				);
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
