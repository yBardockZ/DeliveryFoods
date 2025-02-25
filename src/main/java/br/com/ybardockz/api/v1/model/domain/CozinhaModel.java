package br.com.ybardockz.api.v1.model.domain;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ybardockz.api.v1.model.view.RestauranteView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation  = "cozinhas")
@Getter
@Setter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {
	
	@Schema(example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@Schema(example = "Americana")
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}
