package br.com.ybardockz.api.v1.model.input;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {
	
	@Schema(example = "Picanha Steak")
	@NotBlank
	private String nome;
	
	@Schema(example = "Carne suculenta")
	@NotBlank
	private String descricao;
	
	@Schema(example = "23.50")
	@NotNull
	@PositiveOrZero
	private BigDecimal preco;
	
	@Schema(example = "true")
	@NotNull
	private Boolean ativo;

}
