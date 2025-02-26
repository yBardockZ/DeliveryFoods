package br.com.ybardockz.api.v2.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CozinhaInput")
@Getter
@Setter
public class CozinhaInputV2 {

	@Schema(example = "Brasileira")
	@NotBlank
	private String nome;
	
}
