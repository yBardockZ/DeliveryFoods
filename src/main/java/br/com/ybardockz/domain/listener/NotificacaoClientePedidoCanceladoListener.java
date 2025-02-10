package br.com.ybardockz.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.ybardockz.domain.event.PedidoCanceladoEvent;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.service.EnvioEmailService;
import br.com.ybardockz.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto("Pedido Cancelado")
				.corpo("pedido-cancelado.html")
				.destinatario(pedido.getCliente().getEmail())
				.variavel("pedido", pedido)
				.build();
		
		envioEmail.enviar(mensagem);
	}

}
