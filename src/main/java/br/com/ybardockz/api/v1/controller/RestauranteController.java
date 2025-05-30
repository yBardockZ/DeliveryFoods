package br.com.ybardockz.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import br.com.ybardockz.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import br.com.ybardockz.api.v1.assembler.RestauranteBasicoModelAssembler;
import br.com.ybardockz.api.v1.assembler.RestauranteInputDisassembler;
import br.com.ybardockz.api.v1.assembler.RestauranteModelAssembler;
import br.com.ybardockz.api.v1.model.domain.RestauranteApenasNomeModel;
import br.com.ybardockz.api.v1.model.domain.RestauranteBasicoModel;
import br.com.ybardockz.api.v1.model.domain.RestauranteModel;
import br.com.ybardockz.api.v1.model.input.RestauranteInput;
import br.com.ybardockz.api.v1.openapi.controller.RestauranteControllerOpenApi;
import br.com.ybardockz.domain.exception.CidadeNaoEncontradaException;
import br.com.ybardockz.domain.exception.CozinhaNaoEncontradaException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.RestauranteRepository;
import br.com.ybardockz.domain.service.CadastroRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/restaurante",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CadastroRestauranteService service;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	//@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public ResponseEntity<CollectionModel<RestauranteBasicoModel>> listar() {
		CollectionModel<RestauranteBasicoModel> restaurantesModel = restauranteBasicoModelAssembler
				.toCollectionModel(repository.findAll());
		
		return ResponseEntity.ok(restaurantesModel);
	}
	
	//@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	@Operation(hidden = true)
	public ResponseEntity<CollectionModel<RestauranteApenasNomeModel>> listarApenasNome() {
		CollectionModel<RestauranteApenasNomeModel> restauranteModel = restauranteApenasNomeAssembler
				.toCollectionModel(repository.findAll());
		
		return ResponseEntity.ok(restauranteModel);
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
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		service.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativacoes")
	public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		service.ativar(restauranteIds);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/ativacoes")
	public ResponseEntity<Void> desativarMultiplos(@RequestBody List<Long> restauranteIds) {
		service.inativar(restauranteIds);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(path = "/{restauranteId}/inativar")
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		service.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/abrir")
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		service.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{restauranteId}/fechar")
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		service.fechar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(path = "/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId) {
		service.remover(restauranteId);
	}

}
