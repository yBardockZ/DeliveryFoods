package br.com.ybardockz.api.v1.model.domain;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

	//@JsonView(value = {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	//@JsonView(value = {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	
	//@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	//@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModel endereco;
	
}
