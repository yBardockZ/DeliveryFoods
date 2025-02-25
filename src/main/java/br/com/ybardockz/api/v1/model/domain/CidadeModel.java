package br.com.ybardockz.api.v1.model.domain;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
 
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "Campinas")
	private String nome;
	
	@Schema(description = "Informações do estado")
	private EstadoModel estado;
	
}
