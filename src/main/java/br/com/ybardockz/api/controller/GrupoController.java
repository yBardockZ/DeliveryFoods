package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.ybardockz.api.controller.openapi.GrupoControllerOpenApi;
import br.com.ybardockz.api.model.assembler.GrupoInputDisassembler;
import br.com.ybardockz.api.model.assembler.GrupoModelAssembler;
import br.com.ybardockz.api.model.domain.GrupoModel;
import br.com.ybardockz.api.model.input.GrupoInput;
import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.repository.GrupoRepository;
import br.com.ybardockz.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/grupo", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService service;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
		
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscarPorId(@PathVariable Long grupoId) {
		return grupoModelAssembler.toModel(service.buscarOuFalhar(grupoId));
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoDomain = grupoInputDisassembler.toDomainObject(grupoInput);
		
		return grupoModelAssembler.toModel(service.salvar(grupoDomain));
		
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtualDomain = service.buscarOuFalhar(grupoId);
		
		grupoInputDisassembler.copyToDomain(grupoInput, grupoAtualDomain);
		
		return grupoModelAssembler.toModel(service.salvar(grupoAtualDomain));
	}
	
	@DeleteMapping("/{grupoId}")
	public void deletar(@PathVariable Long grupoId) {
		service.remover(grupoId);
	}
	
}
