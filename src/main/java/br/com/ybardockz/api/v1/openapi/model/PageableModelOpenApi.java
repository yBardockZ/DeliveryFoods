package br.com.ybardockz.api.v1.openapi.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Pageable")
public class PageableModelOpenApi {
	
	private int page;
	private int size;
	private List<String> sort;
	

}
