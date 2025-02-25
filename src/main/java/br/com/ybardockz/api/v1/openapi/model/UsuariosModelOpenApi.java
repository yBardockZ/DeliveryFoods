package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.v1.model.domain.UsuarioModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "UsuariosModel")
@Getter
@Setter
public class UsuariosModelOpenApi {
	
	@JsonProperty("_embedded")
	private UsuariosEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;
	
	@Schema(name = "UsuariosEmbeddedModel")
	@Data
	public class UsuariosEmbeddedModelOpenApi {
		
		List<UsuarioModel> usuarios;
		
	}

}
