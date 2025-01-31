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

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ybardockz.api.model.assembler.RestauranteInputDisassembler;
import br.com.ybardockz.api.model.assembler.RestauranteModelAssembler;
import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.api.model.input.RestauranteInput;
import br.com.ybardockz.api.model.view.RestauranteView;
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
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public ResponseEntity<List<RestauranteModel>> listar() {
		List<Restaurante> restaurantes = repository.findAll();
		
		return ResponseEntity.ok(restauranteModelAssembler.toCollectionModel(restaurantes));
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public ResponseEntity<List<RestauranteModel>> listarApenasNome() {
		return listar();
	}
	
	/*@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
		List<Restaurante> restaurantes = repository.findAll();
		
		List<RestauranteModel> restaurantesModel = 
				restauranteModelAssembler.toCollectionModel(restaurantes);
		
		MappingJacksonValue jacksonWrapper = new MappingJacksonValue(restaurantesModel);
		
		jacksonWrapper.setSerializationView(RestauranteView.Resumo.class);
		
		if ("apenas-nome".equals(projecao)) {
			jacksonWrapper.setSerializationView(RestauranteView.ApenasNome.class);
		} else if ("completo".equals(projecao)) {
			jacksonWrapper.setSerializationView(null);
		}
		
		return jacksonWrapper;

	}*/
	
	
	@GetMapping(path = "/{restauranteId}")
	public RestauranteModel buscarPorId(@PathVariable Long restauranteId) {
		Restaurante restaurante = service.buscarOuFalhar(restauranteId);
		
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
	
	@PutMapping(path = "/{restauranteId}")
	public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput,
			@PathVariable Long restauranteId) {
		try {
			Restaurante restauranteAtual = service.buscarOuFalhar(restauranteId);
			
			restauranteInputDisassembler.copyToDomain(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(service.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
			
		
	}
	
	@PutMapping(path = "/{restauranteId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		service.ativar(restauranteId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		service.ativar(restauranteIds);
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarMultiplos(@RequestBody List<Long> restauranteIds) {
		service.inativar(restauranteIds);
	}
	
	@DeleteMapping(path = "/{restauranteId}/inativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		service.inativar(restauranteId);
		
	}

	@PutMapping("/{restauranteId}/abrir")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		service.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		service.fechar(restauranteId);
	}
	
	@DeleteMapping(path = "/{restauranteId}")
	public void remover(@PathVariable Long restauranteId) {
		service.remover(restauranteId);
	}

}
