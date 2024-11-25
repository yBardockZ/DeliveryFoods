package br.com.ybardockz.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ybardockz.domain.exception.CozinhaNaoEncontradaException;
import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.EntidadeNaoEncontradaException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.RestauranteRepository;
import br.com.ybardockz.domain.service.CadastroRestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CadastroRestauranteService service;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		List<Restaurante> restaurantes = repository.findAll();
		
		return ResponseEntity.ok(restaurantes);
	}
	
	@GetMapping(path = "/{id}")
	public Restaurante buscarPorId(@PathVariable Long id) {
		return service.buscarOuFalhar(id);
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
		try {
			return service.salvar(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping(path = "/{id}")
	public Restaurante atualizar(@RequestBody @Valid Restaurante restaurante, @PathVariable Long id) {
		try {
			Restaurante restauranteExistente = service.buscarOuFalhar(id);
			
			BeanUtils.copyProperties(restaurante, restauranteExistente, "id", "formasDePagamento", "endereco",
						"dataCadastro");
			return service.salvar(restauranteExistente);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
			
		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			service.remover(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@PatchMapping(path = "/{id}")
	private Restaurante atualizarParcialmente(@PathVariable Long id, 
			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteAtual = service.buscarOuFalhar(id);
		
		merge(campos, restauranteAtual, request);
		
		return atualizar(restauranteAtual, id);
		
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		
		ServletServerHttpRequest httpInputMessage = new ServletServerHttpRequest(request);
		
		try {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		Restaurante restauranteOrigem = mapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object value = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, value);
			
	
		});
		}
		catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, httpInputMessage);		
		}
	}

}
