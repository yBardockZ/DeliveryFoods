package br.com.ybardockz.api.openapi.model;

import java.util.List;

import br.com.ybardockz.api.model.domain.GrupoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "GruposModel")
@Data
public class GruposModelOpenApi {
	
	private GruposEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	
	public class GruposEmbeddedModelOpenApi {
		
		List<GrupoModel> grupos;
		
	}

}
