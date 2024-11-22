package br.com.ybardockz.api.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinha")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CadastroCozinhaService service;
	
	@GetMapping
	private ResponseEntity<List<Cozinha>> listar() {
		List<Cozinha> cozinhas = repository.findAll();
		
		return ResponseEntity.ok().body(cozinhas);
	}
	
	@GetMapping(path = "/{id}")
	private Cozinha buscar(@PathVariable Long id) {
		return service.buscarOuFalhar(id);
		
	}
	
	@PostMapping
	private ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
		Cozinha cozinhaSalva = service.salvar(cozinha);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cozinhaSalva.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(cozinhaSalva);
	}
	
	@PutMapping(path = "/{id}")
	private Cozinha atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		Cozinha cozinhaAtual = service.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		return service.salvar(cozinhaAtual);

	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void remover(@PathVariable Long id) {
		service.remover(id);
	}
	
}
