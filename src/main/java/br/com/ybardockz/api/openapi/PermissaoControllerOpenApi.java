package br.com.ybardockz.api.openapi;

import org.springframework.hateoas.CollectionModel;

import br.com.ybardockz.api.model.domain.PermissaoModel;
import br.com.ybardockz.api.openapi.model.PermissoesModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Permissões", description = "Gerencia as permissões")
public interface PermissaoControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada.",
				content = @Content(schema = @Schema(implementation = PermissoesModelOpenApi.class))),
		
	})
	@Operation(summary = "Lista as formas de pagamento")
	public CollectionModel<PermissaoModel> listar();

}
