package br.com.ybardockz.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ybardockz.api.model.assembler.CozinhaInputDisassembler;
import br.com.ybardockz.api.model.assembler.CozinhaModelAssembler;
import br.com.ybardockz.api.model.domain.CozinhaModel;
import br.com.ybardockz.api.model.input.CozinhaInput;
import br.com.ybardockz.api.openapi.controller.CozinhaControllerOpenApi;
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/cozinha", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {
	
	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CadastroCozinhaService service;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = repository.findAll(pageable);
		
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return cozinhasPagedModel;
	}
	
	@GetMapping(path = "/{id}")
	public CozinhaModel buscar(@PathVariable Long id) {
		return cozinhaModelAssembler.toModel(service.buscarOuFalhar(id));
		
	}
	
	@PostMapping
	public ResponseEntity<CozinhaModel> salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaSalva = service.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cozinhaSalva.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(cozinhaModelAssembler.toModel(cozinhaSalva));
	}
	
	@PutMapping(path = "/{id}")
	public CozinhaModel atualizar(@RequestBody @Valid CozinhaInput cozinhaInput, 
			@PathVariable Long id) {
		Cozinha cozinhaAtual = service.buscarOuFalhar(id);
		
		cozinhaInputDisassembler.copyToDomain(cozinhaInput, cozinhaAtual);
		
		return cozinhaModelAssembler.toModel(service.salvar(cozinhaAtual));

	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}
	
}
