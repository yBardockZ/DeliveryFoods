package br.com.ybardockz.api.model.mixin;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.model.Endereco;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.model.Produto;

public class RestauranteMixin {

	@JsonIgnore
	private List<FormaPagamento> formasDePagamento = new ArrayList<>();
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	//@JsonIgnore
	private Instant dataCadastro;
	
	//@JsonIgnore
	private Instant dataAtualizacao;
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
}
