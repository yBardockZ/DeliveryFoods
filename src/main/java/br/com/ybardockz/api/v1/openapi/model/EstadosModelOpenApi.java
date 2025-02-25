package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.v1.model.domain.EstadoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "EstadosModel")
@Data
public class EstadosModelOpenApi {
	
	@JsonProperty("_embedded")
	private EstadosEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;

	@Schema(name = "EstadosEmbeddedModel")
	@Data
	public class EstadosEmbeddedModelOpenApi {

		private List<EstadoModel> estados;

	}

}
