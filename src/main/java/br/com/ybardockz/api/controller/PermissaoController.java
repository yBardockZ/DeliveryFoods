package br.com.ybardockz.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.PermissaoModelAssembler;
import br.com.ybardockz.api.model.domain.PermissaoModel;
import br.com.ybardockz.api.openapi.PermissaoControllerOpenApi;
import br.com.ybardockz.domain.repository.PermissaoRepository;


@RestController
@RequestMapping(path = "/permissao", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {
	
	@Autowired
	private PermissaoRepository repository;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@GetMapping
	public CollectionModel<PermissaoModel> listar() {
		return permissaoModelAssembler
				.toCollectionModel(repository.findAll());
	
	}

}
