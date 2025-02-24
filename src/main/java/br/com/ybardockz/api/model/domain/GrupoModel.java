package br.com.ybardockz.api.model.domain;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoModel extends RepresentationModel<GrupoModel> {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "Admininstrador")
	private String nome;

}
