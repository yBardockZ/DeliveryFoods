package br.com.ybardockz.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estatísticas", description = "Estatísticas da AlgaFood")
public interface EstatisticasControllerOpenApi {

	
	@Parameters({
		
		@Parameter(
			name = "dataInicio",
			description = "Data inicial do período",
			in = ParameterIn.QUERY,
			example = "2025-10-30T00:00:00Z"
			
				),
		
		@Parameter(
				name = "dataFim",
				description = "Data final do período",
				in = ParameterIn.QUERY,
				example = "2025-12-24T03:00:00Z"
					),
		
		@Parameter(
				name = "restauranteId",
				description = "Id do restaurante que deseja visualizar as vendas",
				in = ParameterIn.QUERY,
				example = "1"
					)
		
	})
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Relatório gerado",
				content = {
						@Content(mediaType = MediaType.APPLICATION_PDF_VALUE, 
								schema = @Schema(type = "String", contentMediaType = "binary")),
						
						@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = VendaDiaria.class))
				}),
		
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
			content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID do restaurante inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Consulta vendas diárias")
	public List<VendaDiaria> consultarVendasDiarias(
			@Parameter(required = false, hidden = true) VendaDiariaFilter filter, 
			@Parameter(required = false, description = "Fuso-horário desejado", example = "-00:00") 
			String timeOffSet);
	
	@Operation(summary = "Consulta vendas diárias", hidden = true)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter, String timeOffSet);

}
