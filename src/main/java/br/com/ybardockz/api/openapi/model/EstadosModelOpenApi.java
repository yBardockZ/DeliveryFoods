package br.com.ybardockz.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.ybardockz.api.model.domain.EstadoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "EstadosModel")
@Data
public class EstadosModelOpenApi {
	
	@JsonProperty("_embedded")
	private EstadosEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private Links _links;

	@Schema(name = "EstadosEmbeddedModel")
	@Data
	public class EstadosEmbeddedModelOpenApi {

		private List<EstadoModel> estados;

	}

}
