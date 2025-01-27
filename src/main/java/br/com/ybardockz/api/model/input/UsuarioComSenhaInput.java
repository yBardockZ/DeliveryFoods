package br.com.ybardockz.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput {
	
	@NotBlank
	private String nome;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;

}
