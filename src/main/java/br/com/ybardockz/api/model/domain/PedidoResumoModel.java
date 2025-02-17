package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.ybardockz.domain.model.enums.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoModel {
	
	@Schema(example = "1")
	private String codigo;
	
	@Schema(example = "50.2")
	private BigDecimal subTotal;
	
	@Schema(example = "2.0", description = "Frete do restaurante")
	private BigDecimal taxaFrete;
	
	@Schema(example = "52.2")
	private BigDecimal  valorTotal;
	
	@Schema(example = "CONFIRMADO", description = "Status do pedido")
	private StatusPedido status;
	
	@Schema(example = "2025-01-01T21:10:00Z")
	private Instant dataCriacao;
	
	@Schema(description = "Restaurante onde o pedido foi realizado")
	private RestauranteResumoModel restaurante;
	//private UsuarioModel cliente;
	
	@Schema(description = "Nome do cliente que realizou o pedido")
	private String nomeCliente;
	

}
