package br.com.ybardockz.api.v2.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Links")
@Getter
@Setter
public class LinksModelOpenApiV2 {
	
	private LinkModel ref;
	
	@Schema(name = "Link")
	@Getter
	@Setter
	public class LinkModel {
		
		private String href;
		private boolean templated;
		
	}

}
