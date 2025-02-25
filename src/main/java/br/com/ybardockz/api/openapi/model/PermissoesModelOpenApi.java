package br.com.ybardockz.api.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ybardockz.api.model.domain.PermissaoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "PermissoesModel")
@Getter
@Setter
public class PermissoesModelOpenApi {
	
	@JsonProperty("_embedded")
	private PermissoesModelEmbeddedOpenApi _embedded;

	@JsonProperty("_links")
	private LinksModelOpenApi _links;
	
	@Schema(name = "PermissoesEmbeddedModel")
	@Data
	public class PermissoesModelEmbeddedOpenApi {
		
		List<PermissaoModel> permissoes;
		
	}

}
