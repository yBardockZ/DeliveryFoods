package br.com.ybardockz.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@Schema(example = "Campinas")
	@NotBlank
	private String nome;
	
	@Schema(description = "Informações do estado")
	@Valid
	@NotNull
	private EstadoIdInput estado;

}
