package br.com.ybardockz.api.v2.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "PageModel")
@Getter
@Setter
public class PageModelOpenApiV2 {
	
	@Schema(description = "Quantidade de itens por página", example = "10")
	private int size;
	
	@Schema(description = "Total de registros", example = "100")
	private int totalElements;
	
	@Schema(description = "Total de páginas", example = "10")
	private int totalPages;
	
	@Schema(description = "Número da página atual", example = "0")
	private int number;


}
