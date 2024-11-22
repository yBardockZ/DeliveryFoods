package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.EntidadeNaoEncontradaException;
import br.com.ybardockz.domain.model.Estado;
import br.com.ybardockz.domain.repository.EstadoRepository;
import br.com.ybardockz.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estado")
public class EstadoController {
	
	@Autowired
	private EstadoRepository repository;
	
	@Autowired
	private CadastroEstadoService service;
	
	@GetMapping
	private ResponseEntity<List<Estado>> listar() {
		List<Estado> estados = repository.findAll();
		
		return ResponseEntity.ok().body(estados);
	}
	
	@GetMapping(path = "/{id}")
	private Estado buscarPorId(@PathVariable Long id) {
		return service.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private Estado adicionar(@RequestBody Estado estado) {
		return service.salvar(estado);
	}
	
	@PutMapping(path = "/{id}")
	private Estado atualizar(@RequestBody Estado estado, @PathVariable Long id) {
		Estado estadoExistente = service.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(estado, estadoExistente, "id");
		
		return service.salvar(estadoExistente);
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			service.remover(id);
			
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
