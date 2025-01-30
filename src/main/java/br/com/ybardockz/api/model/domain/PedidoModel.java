package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import br.com.ybardockz.domain.model.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {
	
	private String codigo;
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal  valorTotal;
	private StatusPedido status;
	private Instant dataCriacao;
	private Instant dataConfirmacao;
	private Instant dataCancelamento;
	private Instant dataEntrega;
	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
	private EnderecoModel enderecoEntrega;
	private FormaPagamentoModel formaPagamento;
	private List<ItemPedidoModel> itens;
	

}
