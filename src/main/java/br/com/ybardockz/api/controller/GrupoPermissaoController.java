package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.PermissaoModelAssembler;
import br.com.ybardockz.api.model.domain.PermissaoModel;
import br.com.ybardockz.domain.model.Permissao;
import br.com.ybardockz.domain.repository.PermissaoRepository;
import br.com.ybardockz.domain.service.CadastroGrupoService;
import br.com.ybardockz.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/grupo/{grupoId}/permissao")
public class GrupoPermissaoController {
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId) {
		cadastroGrupoService.buscarOuFalhar(grupoId);
		
		List<Permissao> permissoes = permissaoRepository.findPermissoesByGrupoId(grupoId);
		
		return permissaoModelAssembler.toCollectionModel(permissoes);
	}
	
	@GetMapping("/{permissaoId}")
	public PermissaoModel buscarPorId(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		Permissao permissao = cadastroPermissaoService.buscarOuFalharPorGrupo(grupoId, permissaoId);
		
		return permissaoModelAssembler.toModel(permissao);
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.associar(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.disassociar(grupoId, permissaoId);
	}

}
