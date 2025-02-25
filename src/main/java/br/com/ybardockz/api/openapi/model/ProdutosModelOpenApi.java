package br.com.ybardockz.api.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.model.domain.ProdutoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ProdutosModel")
@Getter
@Setter
public class ProdutosModelOpenApi {
	
	@JsonProperty("_embedded")
	private ProdutosModelEmbeddedOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;

	@Schema(name = "ProdutosEmbeddedModel")
	@Getter
	@Setter
	public class ProdutosModelEmbeddedOpenApi {
		
		List<ProdutoModel> produtos;
		
	}

}
