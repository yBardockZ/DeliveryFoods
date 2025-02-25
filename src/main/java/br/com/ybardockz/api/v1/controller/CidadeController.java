package br.com.ybardockz.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.ResourceUriHelper;
import br.com.ybardockz.api.v1.assembler.CidadeInputDisassembler;
import br.com.ybardockz.api.v1.assembler.CidadeModelAssembler;
import br.com.ybardockz.api.v1.model.domain.CidadeModel;
import br.com.ybardockz.api.v1.model.input.CidadeInput;
import br.com.ybardockz.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.com.ybardockz.core.web.AlgaMediaTypes;
import br.com.ybardockz.domain.exception.EstadoNaoEncontradoException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.repository.CidadeRepository;
import br.com.ybardockz.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/cidade",
	produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroCidadeService service;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		CollectionModel<CidadeModel> cidadesCollectionModel = cidadeModelAssembler.toCollectionModel(repository.findAll());
		
		return cidadesCollectionModel;
	}
	
	@GetMapping(path = "/{id}")
	public CidadeModel buscarPorId(@PathVariable Long id) {
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(service.buscarOuFalhar(id));
		
		return cidadeModel;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeDomain = cidadeInputDisassembler.toDomainObject(cidadeInput);
			CidadeModel cidadeModel =  cidadeModelAssembler.toModel(service.salvar(cidadeDomain));
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
			
			return cidadeModel;
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PutMapping(path = "/{id}")
	public CidadeModel atualizar(@RequestBody @Valid CidadeInput cidadeInput, @PathVariable Long id) {
		try {
			Cidade cidadeExistente = service.buscarOuFalhar(id);
			
			cidadeInputDisassembler.copyToDomain(cidadeInput, cidadeExistente);

			return cidadeModelAssembler.toModel(service.salvar(cidadeExistente));

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping(path = "/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
