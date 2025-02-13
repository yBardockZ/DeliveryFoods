package br.com.ybardockz.api.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "João")
	private String nome;
	
	@Schema(example = "joao@gmail.com")
	private String email;
	
}
