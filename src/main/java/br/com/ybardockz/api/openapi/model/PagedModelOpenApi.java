package br.com.ybardockz.api.openapi.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedModelOpenApi<T> {
	
	@Schema(description = "Lista de elementos")
	private List<T> content;
	
	@Schema(description = "Quantidade de itens por página", example = "10")
	private int size;
	
	@Schema(description = "Total de registros", example = "100")
	private int totalElements;
	
	@Schema(description = "Total de páginas", example = "10")
	private int totalPages;
	
	@Schema(description = "Número da página atual", example = "0")
	private int number;


}

