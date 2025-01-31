package br.com.ybardockz.domain.repository.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {
	
	private Long clienteId;
	private Long restauranteId;
	private Instant dataCriacaoInicio;
	private Instant dataCriacaoFim;

}
