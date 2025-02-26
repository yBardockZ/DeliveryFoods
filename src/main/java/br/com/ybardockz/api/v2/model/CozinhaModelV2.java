package br.com.ybardockz.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CozinhaModel")
@Relation(collectionRelation  = "cozinhas")
@Getter
@Setter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {
	
	@Schema(example = "1")
	private Long idCozinha;
	
	@Schema(example = "Americana")
	private String nomeCozinha;
	
}
