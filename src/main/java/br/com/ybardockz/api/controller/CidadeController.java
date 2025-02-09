package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.ybardockz.api.model.assembler.CidadeInputDisassembler;
import br.com.ybardockz.api.model.assembler.CidadeModelAssembler;
import br.com.ybardockz.api.model.domain.CidadeModel;
import br.com.ybardockz.api.model.input.CidadeInput;
import br.com.ybardockz.domain.exception.EstadoNaoEncontradoException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.repository.CidadeRepository;
import br.com.ybardockz.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroCidadeService service;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	private ResponseEntity<List<CidadeModel>> listar() {
		List<Cidade> cidades = repository.findAll();

		return ResponseEntity.ok(cidadeModelAssembler.toCollectionModel(cidades));
	}

	@GetMapping(path = "/{id}")
	private CidadeModel buscarPorId(@PathVariable Long id) {
		return cidadeModelAssembler.toModel(service.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeDomain = cidadeInputDisassembler.toDomainObject(cidadeInput);
			return cidadeModelAssembler.toModel(service.salvar(cidadeDomain));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PutMapping(path = "/{id}")
	private Cidade atualizar(@RequestBody @Valid CidadeInput cidadeInput, @PathVariable Long id) {
		try {
			Cidade cidadeExistente = service.buscarOuFalhar(id);
			
			cidadeInputDisassembler.copyToDomain(cidadeInput, cidadeExistente);

			return service.salvar(cidadeExistente);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping(path = "/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
