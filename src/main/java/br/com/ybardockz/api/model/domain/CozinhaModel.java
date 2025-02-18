package br.com.ybardockz.api.model.domain;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ybardockz.api.model.view.RestauranteView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModel {
	
	@Schema(example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@Schema(example = "Americana")
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
}
