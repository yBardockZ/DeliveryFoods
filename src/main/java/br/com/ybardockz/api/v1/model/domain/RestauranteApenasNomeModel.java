package br.com.ybardockz.api.v1.model.domain;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteApenasNomeModel extends RepresentationModel<RestauranteApenasNomeModel> {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "McDonalds")
	private String nome;

}
