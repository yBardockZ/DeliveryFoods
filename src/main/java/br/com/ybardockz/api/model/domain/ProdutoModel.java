package br.com.ybardockz.api.model.domain;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "Picanha Steak")
	private String nome;
	
	@Schema(example = "Carne suculenta")
	private String descricao;
	
	@Schema(example = "23.50")
	private BigDecimal preco;
	
	@Schema(example = "true")
	private Boolean ativo;

}
