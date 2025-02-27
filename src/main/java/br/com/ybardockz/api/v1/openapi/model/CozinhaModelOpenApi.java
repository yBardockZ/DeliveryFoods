package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.ybardockz.api.v1.model.domain.CozinhaModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CozinhaPaginada")
@Getter
@Setter
@JsonPropertyOrder({ "_embedded", "_links", "page" })
public class CozinhaModelOpenApi {
	
	@JsonProperty("_embedded")
	private CozinhasEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;

	private PageModelOpenApi page;
	
	@Schema(name = "CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaModel> cozinhas;
		
	}
	
}
