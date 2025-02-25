package br.com.ybardockz.api.v2.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputV2 {

	@Schema(example = "Campinas")
	@NotBlank
	private String nome;
	
	@NotNull
	private Long idEstado;
	
}
