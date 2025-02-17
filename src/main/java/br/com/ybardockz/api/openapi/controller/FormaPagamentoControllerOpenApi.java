package br.com.ybardockz.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.FormaPagamentoModel;
import br.com.ybardockz.api.model.input.FormaPagamentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Formas de pagamento", description = "Gerencia as formas de pagamento")
public interface FormaPagamentoControllerOpenApi {
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = FormaPagamentoModel.class)))
		
	})
	@Operation(summary = "Lista as formas de pagamento")
	ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);

	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
				content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca uma forma de pagamento")
	ResponseEntity<FormaPagamentoModel> buscarPorId(@Parameter(required = true) Long formaPagamentoId,
			ServletWebRequest request);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada",
				content = @Content(schema = @Schema(implementation = FormaPagamentoModel.class)))
		
	})
	@Operation(summary = "Cadastra uma forma de pagamento")
	public FormaPagamentoModel adicionar(FormaPagamentoInput formaPagamentoInput);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Atualiza uma forma de pagamento")
	public FormaPagamentoModel atualizar(FormaPagamentoInput formaPagamentoInput,
			@Parameter(required = true) Long formaPagamentoId);
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID inválido",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "204", description = "Forma de pagamento removida")
				
	})
	@Operation(summary = "Remove uma forma de pagamento")
	public void remover(@Parameter(required = true) Long formaPagamentoId);
	
}
