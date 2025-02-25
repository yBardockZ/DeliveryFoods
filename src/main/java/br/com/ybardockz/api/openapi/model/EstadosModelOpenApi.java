package br.com.ybardockz.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.com.ybardockz.api.model.domain.EstadoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "EstadosModel")
@Getter
@Setter
public class EstadosModelOpenApi {
	
	private EstadosEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(name = "EstadosEmbeddedModel")
	@Data
	public class EstadosEmbeddedModelOpenApi {

		private List<EstadoModel> estados;

	}

}
