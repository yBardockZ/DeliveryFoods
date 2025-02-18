package br.com.ybardockz.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.api.model.input.RestauranteInput;
import br.com.ybardockz.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface RestauranteControllerOpenApi {
	
	@Parameters({
		
		@Parameter(
				name = "projecao",
				description = "Nome da projeção de restaurantes",
				in = ParameterIn.QUERY,
				schema = @Schema(allowableValues = {"apenas-nome"})
				)
		
	})
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Consulta realizada",
				content = @Content(schema = @Schema(implementation = RestauranteBasicoModelOpenApi.class)))
		
	})
	@Operation(summary = "Lista os restaurantes")
	public ResponseEntity<List<RestauranteModel>> listar();
	
	ResponseEntity<List<RestauranteModel>> listarApenasNome();
	
	public RestauranteModel buscarPorId(Long restauranteId);
	
	RestauranteModel adicionar(RestauranteInput restauranteInput);
	
	RestauranteModel atualizar(RestauranteInput restauranteInput, Long restauranteId);
	
	void ativar(Long restauranteId);
	
	void ativarMultiplos(List<Long> restauranteIds);
	
	void desativarMultiplos(List<Long> restauranteIds);
	
	void inativar(Long restauranteId);

	void abrir(Long restauranteId);

	void fechar(Long restauranteId);	

	void remover(Long restauranteId);

}
