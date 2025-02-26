package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.ybardockz.api.v1.model.domain.PedidoResumoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "PedidosResumoModel")
@Data
@JsonPropertyOrder({"_embedded", "_links", "page"})
public class PedidosResumoModelOpenApi {
	
	@JsonProperty("_embedded")
	private PedidosResumoEmbeddedModelOpenApi _embedded;
	
	@JsonProperty("_links")
	private LinksModelOpenApi _links;
	
	private PageModelOpenApi page;
	
	@Schema(name = "PedidosResumoEmbeddedModel")
	@Data
	public class PedidosResumoEmbeddedModelOpenApi {
		
		List<PedidoResumoModel> pedidos;
		
	}

}
