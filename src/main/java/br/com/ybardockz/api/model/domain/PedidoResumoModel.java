package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFilter;

import br.com.ybardockz.domain.model.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel {
	
	private String codigo;
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal  valorTotal;
	private StatusPedido status;
	private Instant dataCriacao;
	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
	

}
