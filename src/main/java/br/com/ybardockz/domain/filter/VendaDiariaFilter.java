package br.com.ybardockz.domain.filter;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiariaFilter {
	
	private LocalDate dataInicio;
	private	LocalDate dataFim;
	private Long restauranteId;

}
