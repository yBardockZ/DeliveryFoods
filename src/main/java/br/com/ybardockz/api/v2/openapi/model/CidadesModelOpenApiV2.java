package br.com.ybardockz.api.v2.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.v2.model.CidadeModelV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CidadesModel")
@Getter
@Setter
public class CidadesModelOpenApiV2 {
	
	@JsonProperty("_embedded")
	private CidadesEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApiV2 _links;
	
	@Schema(name = "CidadesEmbeddedModel")
	@Data
	public class CidadesEmbeddedModelOpenApi {
		
		List<CidadeModelV2> cidades;
		
	}

}
