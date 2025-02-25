package br.com.ybardockz.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.model.domain.CidadeModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CidadesModel")
@Getter
@Setter
public class CidadesModelOpenApi {
	
	@JsonProperty("_embedded")
	private CidadeEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private Links _links;
	
	@Schema(name = "CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi {
		
		private List<CidadeModel> cidades;
		
	}

}
