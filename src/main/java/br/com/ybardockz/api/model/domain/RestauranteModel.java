package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {

	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaModel cozinha;
	
}
