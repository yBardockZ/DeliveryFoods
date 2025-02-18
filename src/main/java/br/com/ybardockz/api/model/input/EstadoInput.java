package br.com.ybardockz.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInput {
	
	@Schema(example = "São Paulo")
	@NotBlank
	private String nome;

}
