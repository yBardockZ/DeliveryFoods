package br.com.ybardockz.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String senhaNova;

}
