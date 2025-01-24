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

import br.com.ybardockz.api.model.assembler.RestauranteInputDisassembler;
import br.com.ybardockz.api.model.assembler.RestauranteModelAssembler;
import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.api.model.input.RestauranteInput;
import br.com.ybardockz.domain.exception.CidadeNaoEncontradaException;
import br.com.ybardockz.domain.exception.CozinhaNaoEncontradaException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.RestauranteRepository;
import br.com.ybardockz.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CadastroRestauranteService service;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@GetMapping
	public ResponseEntity<List<RestauranteModel>> listar() {
		List<RestauranteModel> restaurantes = restauranteModelAssembler.toCollectionModel(repository.findAll());
		
		return ResponseEntity.ok(restaurantes);
	}
	
	@GetMapping(path = "/{id}")
	public RestauranteModel buscarPorId(@PathVariable Long id) {
		Restaurante restaurante = service.buscarOuFalhar(id);
		
		RestauranteModel restauranteModel = restauranteModelAssembler.toModel(restaurante);
		
		
		return restauranteModel;
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModel(service.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping(path = "/{id}")
	public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long id) {
		try {
			Restaurante restauranteAtual = service.buscarOuFalhar(id);
			
			restauranteInputDisassembler.copyToDomain(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(service.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
			
		
	}
	
	@PutMapping(path = "/{id}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		service.ativar(id);
	}
	
	@DeleteMapping(path = "/{id}/inativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		service.inativar(id);
		
	}
	
	@DeleteMapping(path = "/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
