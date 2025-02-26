package br.com.ybardockz.api.v2.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.ybardockz.api.v2.model.CozinhaModelV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CozinhasModel")
@Getter
@Setter
@JsonPropertyOrder({"_embedded", "_links", "page"})
public class CozinhasModelOpenApiV2 {
	
	@JsonProperty("_embedded")
	private CozinhasEmbeddedModelOpenApiV2 _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApiV2 _links;
	
	private PageModelOpenApiV2 page;
	
	@Schema(name = "CozinhasEmbeddedModel")
	@Data
	class CozinhasEmbeddedModelOpenApiV2 {
		
		List<CozinhaModelV2> cozinhas;
		
	}

}
