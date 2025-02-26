package br.com.ybardockz.api.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.ybardockz.api.v2.assembler.CidadeInputDisassemblerV2;
import br.com.ybardockz.api.v2.assembler.CidadeModelAssemblerV2;
import br.com.ybardockz.api.v2.model.CidadeModelV2;
import br.com.ybardockz.api.v2.model.input.CidadeInputV2;
import br.com.ybardockz.api.v2.openapi.controller.CidadeControllerOpenApiV2;
import br.com.ybardockz.domain.exception.EstadoNaoEncontradoException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.repository.CidadeRepository;
import br.com.ybardockz.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cidade",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerOpenApiV2 {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroCidadeService service;
	
	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;

	@GetMapping
	public CollectionModel<CidadeModelV2> listar() {
		CollectionModel<CidadeModelV2> cidadesCollectionModel = cidadeModelAssembler.toCollectionModel(repository.findAll());
		
		return cidadesCollectionModel;
	}
	
	@GetMapping(path = "/{id}")
	public CidadeModelV2 buscarPorId(@PathVariable Long id) {
		CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(service.buscarOuFalhar(id));
		
		return cidadeModel;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidadeDomain = cidadeInputDisassembler.toDomainObject(cidadeInput);
			CidadeModelV2 cidadeModel =  cidadeModelAssembler.toModel(service.salvar(cidadeDomain));
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());
			
			return cidadeModel;
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PutMapping(path = "/{id}")
	public CidadeModelV2 atualizar(@RequestBody @Valid CidadeInputV2 cidadeInput, @PathVariable Long id) {
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
