package br.com.ybardockz.api.model.domain;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {
	
	@Schema(example = "1")
	private Long id;
	
	@Schema(example = "Cartão de crédito")
	private String descricao;

}
