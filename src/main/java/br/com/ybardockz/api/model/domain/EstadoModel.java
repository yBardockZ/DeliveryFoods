package br.com.ybardockz.api.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModel {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "São paulo")
	private String nome;
	
}
