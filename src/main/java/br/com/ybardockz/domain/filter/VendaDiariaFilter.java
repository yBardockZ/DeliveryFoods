package br.com.ybardockz.domain.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiariaFilter {
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private	Date dataFim;
	
	private Long restauranteId;

}
