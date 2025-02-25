package br.com.ybardockz.api.v1.model.domain;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "permissoes")
@Getter
@Setter
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "CONSULTAR_USUARIOS")
	private String nome;
	
	@Schema(example = "Acesso a consulta de usuários")
	private String descricao;
	
}
