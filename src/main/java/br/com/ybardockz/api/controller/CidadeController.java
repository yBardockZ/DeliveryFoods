package br.com.ybardockz.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

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
import br.com.ybardockz.api.model.assembler.CidadeInputDisassembler;
import br.com.ybardockz.api.model.assembler.CidadeModelAssembler;
import br.com.ybardockz.api.model.domain.CidadeModel;
import br.com.ybardockz.api.model.input.CidadeInput;
import br.com.ybardockz.api.openapi.controller.CidadeControllerOpenApi;
import br.com.ybardockz.domain.exception.EstadoNaoEncontradoException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.repository.CidadeRepository;
import br.com.ybardockz.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/cidade",
	produces = MediaType.APPLICATION_JSON_VALUE)
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
		List<CidadeModel> cidadesModel = cidadeModelAssembler
				.toCollectionModel(repository.findAll());

		CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(cidadesModel);
		
		cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());
		
		cidadesCollectionModel.forEach(cidadeModel -> {
			cidadeModel.add(linkTo(CidadeController.class).slash(cidadeModel.getId()).withSelfRel());
			
			cidadeModel.add(linkTo(CidadeController.class).withRel("cozinhas"));
			
			cidadeModel.getEstado().add(linkTo(EstadoController.class)
					.slash(cidadeModel.getEstado().getId()).withSelfRel());
			
		});
		
		return cidadesCollectionModel;
	}
	
	@GetMapping(path = "/{id}")
	public CidadeModel buscarPorId(@PathVariable Long id) {
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(service.buscarOuFalhar(id));
		
		cidadeModel.add(linkTo(CidadeController.class).slash(id).withSelfRel());
		
		cidadeModel.add(linkTo(CidadeController.class).withRel("cozinhas"));
		
		cidadeModel.getEstado().add(linkTo(EstadoController.class)
				.slash(cidadeModel.getEstado().getId()).withSelfRel());
		
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
