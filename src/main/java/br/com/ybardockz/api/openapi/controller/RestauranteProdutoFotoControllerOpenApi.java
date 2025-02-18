package br.com.ybardockz.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.domain.FotoProdutoModel;
import br.com.ybardockz.api.model.input.FotoProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Foto atualizada"),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado"),
		
		@ApiResponse(responseCode = "400", description = "ID de Restaurante ou produto inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@RequestBody(content = {
			@Content(
					mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
					schema = @Schema(implementation = FotoProdutoInput.class)
					)
	})
	@Operation(summary = "Faz upload de uma foto para um produto")
	FotoProdutoModel uploadFoto(@Parameter(required = true, example = "1") Long restauranteId,
			@Parameter(required = true, example = "1") Long produtoId, 
			FotoProdutoInput fotoInput) throws IOException;
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "200", description = "Foto encontrada e armazenada como JSON ou imagem",
				content = {
						@Content(schema = @Schema(implementation = FotoProdutoModel.class)),
						@Content(mediaType = "image/jpeg", schema = @Schema(type = "String", format = "binary")),
						@Content(mediaType = "image/png", schema = @Schema(type = "String", format = "binary"))
				}),
		
		@ApiResponse(responseCode = "302", description = "Foto armazenada externamente redirecionamento para URL",
		headers = @Header(name = HttpHeaders.LOCATION, description = "URL da imagem armazenada externamente")),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado",
				content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID de Restaurante ou produto inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Busca uma foto de um produto")
	FotoProdutoModel buscar(@Parameter(required = true, example = "1") Long restauranteId,
			@Parameter(required = true, example = "1") Long produtoId);
	
	@Operation(summary = "Busca uma foto de um produto", hidden = true)
	ResponseEntity<?> sevirFoto(Long restauranteId, Long produtoId, 
			String acceptHeader) 
			throws HttpMediaTypeNotAcceptableException;
	
	@ApiResponses({
		
		@ApiResponse(responseCode = "204", description = "Foto excluida."),
		
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado",
		content = @Content(schema = @Schema(implementation = Problema.class))),
		
		@ApiResponse(responseCode = "400", description = "ID de Restaurante ou produto inválido",
		content = @Content(schema = @Schema(implementation = Problema.class)))
		
	})
	@Operation(summary = "Exclui a foto de um produto")
	void excluir(@PathVariable Long restauranteId,
			@PathVariable Long produtoId);

}
