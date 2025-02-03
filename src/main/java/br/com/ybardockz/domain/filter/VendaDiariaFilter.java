package br.com.ybardockz.domain.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiariaFilter {
	
	private Instant dataInicio;
	private Instant dataFim;
	private Long restauranteId;

}
