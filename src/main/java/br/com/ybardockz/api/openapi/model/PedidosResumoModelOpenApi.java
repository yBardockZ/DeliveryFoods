package br.com.ybardockz.api.openapi.model;

import java.util.List;

import br.com.ybardockz.api.model.domain.PedidoResumoModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "PedidosResumoModel")
@Data
public class PedidosResumoModelOpenApi {
	
	private PedidosResumoEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PagedModelOpenApi<PedidoResumoModel> page;
	
	@Schema(name = "PedidosResumoEmbeddedModel")
	@Data
	public class PedidosResumoEmbeddedModelOpenApi {
		
		List<PedidoResumoModel> pedidos;
		
	}

}
