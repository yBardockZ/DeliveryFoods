package br.com.ybardockz.api.v1.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {
	
	@NotNull
	private Long id;
	
}
