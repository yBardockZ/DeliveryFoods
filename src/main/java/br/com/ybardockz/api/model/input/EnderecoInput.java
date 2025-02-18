package br.com.ybardockz.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
	
	@Schema(example = "54032-022")
	@NotBlank
	private String cep;
	
	@Schema(example = "Av. Paulista n 405")
	@NotBlank
	private String logradouro;
	
	@Schema(example = "33")
	@NotBlank
	private String numero;
	
	@Schema(example = "consultorio 1504")
	private String complemento;
	
	@Schema(example = "Centro")
	@NotBlank
	private String bairro;
	
	@Schema(description = "Id da cidade")
	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
