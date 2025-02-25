package br.com.ybardockz.api.v1.openapi.model;

import java.math.BigDecimal;

import br.com.ybardockz.api.v1.model.domain.CozinhaModel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Tag(name = "RestauranteBasicoModel")
public class RestauranteBasicoModelOpenApi {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "McDonalds")
	private String nome;

	@Schema(example = "2.02")
	private BigDecimal taxaFrete;

	@Schema(description = "Cozinha do restaurante")
	private CozinhaModel cozinha;

}
