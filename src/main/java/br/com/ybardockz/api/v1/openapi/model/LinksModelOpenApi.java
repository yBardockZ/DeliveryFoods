package br.com.ybardockz.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksModelOpenApi {
	
	private LinkModel ref;
	
	@Getter
	@Setter
	public class LinkModel {
		
		private String href;
		private boolean templated;
		
	}

}
