package br.com.ybardockz.api.openapi;

import org.springframework.hateoas.CollectionModel;

import br.com.ybardockz.api.model.domain.PermissaoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Permissões")
public interface PermissaoControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada."),
		
	})
	@Operation(summary = "Lista as formas de pagamento")
	public CollectionModel<PermissaoModel> listar();

}
