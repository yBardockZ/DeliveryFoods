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

import br.com.ybardockz.domain.exception.EntidadeNaoEncontradaException;
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

	@GetMapping
	private ResponseEntity<List<Cidade>> listar() {
		List<Cidade> cidades = repository.findAll();

		return ResponseEntity.ok(cidades);
	}

	@GetMapping(path = "/{id}")
	private Cidade buscarPorId(@PathVariable Long id) {
		return service.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private Cidade adicionar(@RequestBody @Valid Cidade cidade) {
		try {
			return service.salvar(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PutMapping(path = "/{id}")
	private Cidade atualizar(@Valid @RequestBody Cidade cidade, @PathVariable Long id) {
		try {
			Cidade cidadeExistente = service.buscarOuFalhar(id);
			BeanUtils.copyProperties(cidade, cidadeExistente, "id");

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
