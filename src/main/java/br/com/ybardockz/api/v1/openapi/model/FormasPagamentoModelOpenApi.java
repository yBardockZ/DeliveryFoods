package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.ybardockz.api.v1.model.domain.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "FormasPagamentoModel")
@Getter
@Setter
@JsonPropertyOrder({ "_embedded", "_links", "page" })
public class FormasPagamentoModelOpenApi {
	
	@JsonProperty("_embedded")
	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_link")
	private LinksModelOpenApi _link;
	
	@Schema(name = "FormasPagamentoEmbeddedModel")
	@Getter
	@Setter
	public class FormasPagamentoEmbeddedModelOpenApi {
		
		List<FormaPagamentoModel> formasPagamento;
		
	}

}
