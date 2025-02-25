package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.v1.model.domain.GrupoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "GruposModel")
@Data
public class GruposModelOpenApi {
	
	@JsonProperty("_embedded")
	private GruposEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;
	
	@Schema(name = "GruposEmbeddedModel")
	@Data
	public class GruposEmbeddedModelOpenApi {
		
		List<GrupoModel> grupos;
		
	}

}
