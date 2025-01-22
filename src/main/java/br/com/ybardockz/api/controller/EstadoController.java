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

import br.com.ybardockz.api.model.assembler.EstadoInputDisassembler;
import br.com.ybardockz.api.model.assembler.EstadoModelAssembler;
import br.com.ybardockz.api.model.domain.EstadoModel;
import br.com.ybardockz.api.model.input.EstadoInput;
import br.com.ybardockz.domain.model.Estado;
import br.com.ybardockz.domain.repository.EstadoRepository;
import br.com.ybardockz.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estado")
public class EstadoController {
	
	@Autowired
	private EstadoRepository repository;
	
	@Autowired
	private CadastroEstadoService service;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	private ResponseEntity<List<EstadoModel>> listar() {
		List<Estado> estados = repository.findAll();
		
		return ResponseEntity.ok().body(estadoModelAssembler.toCollectionModel(estados));
	}
	
	@GetMapping(path = "/{id}")
	private EstadoModel buscarPorId(@PathVariable Long id) {
		return estadoModelAssembler.toModel(service.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoDomain = estadoInputDisassembler.toDomainObject(estadoInput);
		
		return estadoModelAssembler.toModel(service.salvar(estadoDomain));
	}
	
	@PutMapping(path = "/{id}")
	private EstadoModel atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id) {
		Estado estadoExistente = service.buscarOuFalhar(id);
		
		estadoInputDisassembler.copyToDomain(estadoInput, estadoExistente);
		
		return estadoModelAssembler.toModel(service.salvar(estadoExistente));
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<?> remover(@PathVariable Long id) {
		service.remover(id);
		
		return ResponseEntity.noContent().build();
		
	}
}
