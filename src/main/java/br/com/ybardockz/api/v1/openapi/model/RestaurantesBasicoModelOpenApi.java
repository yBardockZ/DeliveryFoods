package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.v1.model.domain.RestauranteBasicoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "RestaurantesBasicoModel")
@Getter
@Setter
public class RestaurantesBasicoModelOpenApi {
	
	@JsonProperty("_embedded")
	private RestaurantesBasicoModelEmbeddedOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;
	
	@Schema(name = "RestaurantesEmbeddedModel")
	@Data
	public class RestaurantesBasicoModelEmbeddedOpenApi {
		
		List<RestauranteBasicoModel> restaurantes;
		
	}

}
