package br.com.ybardockz.api.v1.model.domain;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import br.com.ybardockz.domain.model.enums.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {
	
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
	private RestauranteApenasNomeModel restaurante;
	
	@Schema(description = "Informações do cliente que realizou o pedido")
	private UsuarioModel cliente;
	

}
