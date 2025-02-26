package br.com.ybardockz.api.v2.controller;

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

import br.com.ybardockz.api.v2.assembler.CozinhaInputDisassemblerV2;
import br.com.ybardockz.api.v2.assembler.CozinhaModelAssemblerV2;
import br.com.ybardockz.api.v2.model.CozinhaModelV2;
import br.com.ybardockz.api.v2.model.input.CozinhaInputV2;
import br.com.ybardockz.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cozinha", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {
	
	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CadastroCozinhaService service;
	
	@Autowired
	private CozinhaModelAssemblerV2 cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@GetMapping
	public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = repository.findAll(pageable);
		
		PagedModel<CozinhaModelV2> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return cozinhasPagedModel;
	}
	
	@GetMapping(path = "/{id}")
	public CozinhaModelV2 buscar(@PathVariable Long id) {
		return cozinhaModelAssembler.toModel(service.buscarOuFalhar(id));
		
	}
	
	@PostMapping
	public ResponseEntity<CozinhaModelV2> salvar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		Cozinha cozinhaSalva = service.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cozinhaSalva.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(cozinhaModelAssembler.toModel(cozinhaSalva));
	}
	
	@PutMapping(path = "/{id}")
	public CozinhaModelV2 atualizar(@RequestBody @Valid CozinhaInputV2 cozinhaInput, 
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
