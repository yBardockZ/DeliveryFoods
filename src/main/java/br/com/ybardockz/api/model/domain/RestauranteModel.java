package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ybardockz.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {

	@JsonView(value = {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	@JsonView(value = {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModel endereco;
	
}
