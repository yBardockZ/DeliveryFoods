package br.com.ybardockz.api.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoModel {

	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "CONSULTAR_USUARIOS")
	private String nome;
	
	@Schema(example = "Acesso a consulta de usuários")
	private String descricao;
	
}
