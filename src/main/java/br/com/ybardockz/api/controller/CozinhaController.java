package br.com.ybardockz.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.domain.service.CadastroCozinhaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozinha")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CadastroCozinhaService service;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Parameters({
        @Parameter(
            name = "page",
            description = "Número da página (começa em 0)",
            in = ParameterIn.QUERY,
            example = "0"
        ),
        @Parameter(
            name = "size",
            description = "Quantidade de itens por página",
            in = ParameterIn.QUERY,
            example = "10"
        ),
        @Parameter(
            name = "sort",
            description = "Critérios de ordenação (ex: 'nome,asc')",
            in = ParameterIn.QUERY,
            example = "nome,asc"
        )
    })
	@GetMapping
	private Page<CozinhaModel> listar(@PageableDefault(size = 10) @Parameter(hidden = true) Pageable pageable) {
		Page<Cozinha> cozinhasPage = repository.findAll(pageable);
		
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		
		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable,
				cozinhasPage.getTotalElements());
		
		return cozinhasModelPage;
	}
	
	@GetMapping(path = "/{id}")
	private CozinhaModel buscar(@PathVariable Long id) {
		return cozinhaModelAssembler.toModel(service.buscarOuFalhar(id));
		
	}
	
	@PostMapping
	private ResponseEntity<CozinhaModel> salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaSalva = service.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cozinhaSalva.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(cozinhaModelAssembler.toModel(cozinhaSalva));
	}
	
	@PutMapping(path = "/{id}")
	private CozinhaModel atualizar(@RequestBody @Valid CozinhaInput cozinhaInput, 
			@PathVariable Long id) {
		Cozinha cozinhaAtual = service.buscarOuFalhar(id);
		
		cozinhaInputDisassembler.copyToDomain(cozinhaInput, cozinhaAtual);
		
		return cozinhaModelAssembler.toModel(service.salvar(cozinhaAtual));

	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void remover(@PathVariable Long id) {
		service.remover(id);
	}
	
}
