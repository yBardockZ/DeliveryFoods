package br.com.ybardockz.api.v1.model.domain;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {
	
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
