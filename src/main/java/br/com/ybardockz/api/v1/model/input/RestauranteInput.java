package br.com.ybardockz.api.v1.model.input;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInput {

	@Schema(example = "McDonalds")
	@NotBlank
	private String nome;
	
	@Schema(example = "2.02")
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Schema(example = "Americana")
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;
	
	@Schema(description = "Endereço do restaurante")
	@NotNull
	@Valid
	private EnderecoInput endereco;
	
}
