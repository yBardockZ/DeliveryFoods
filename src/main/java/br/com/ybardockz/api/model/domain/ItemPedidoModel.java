package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "2")
	private Integer quantidade;
	
	@Schema(example = "13.3")
	private BigDecimal precoUnitario;
	
	@Schema(example = "26.6")
	private BigDecimal precoTotal;
	
	@Schema(example = "Ao ponto, porfavor.")
	private String observacao;
	
	@Schema(example = "1")
	private Long produtoId;
	
	@Schema(example = "Picanha Steak")
	private String produtoNome;
	
}
