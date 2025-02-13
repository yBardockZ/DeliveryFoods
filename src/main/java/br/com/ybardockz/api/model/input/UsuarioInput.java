package br.com.ybardockz.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@Schema(example = "João")
	@NotBlank
	private String nome;
	
	@Schema(example = "joao@gmail.com")
	@Email
	@NotBlank
	private String email;

}
