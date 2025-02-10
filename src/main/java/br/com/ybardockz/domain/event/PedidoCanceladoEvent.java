package br.com.ybardockz.domain.event;

import br.com.ybardockz.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {
	
	private Pedido pedido;

}
