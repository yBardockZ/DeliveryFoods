package br.com.ybardockz.api.model.domain;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ybardockz.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModel {
	
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}
