package br.com.ybardockz.api.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v2.controller.CidadeControllerV2;

@Component
public class AlgaLinksV2 {
	
	public Link linkToCidades(String rel) {
		return linkTo(CidadeControllerV2.class).withRel(rel);
	}
	
	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToCidades(Long cidadeId, String rel) {
		return linkTo(methodOn(CidadeControllerV2.class)
				.buscarPorId(cidadeId)).withRel(rel);
	}
	
	public Link linkToCidades(Long cidadeId) {
		return linkToCidades(cidadeId, IanaLinkRelations.SELF_VALUE);
	}
	
}
