package br.com.ybardockz.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.assembler.PermissaoModelAssembler;
import br.com.ybardockz.api.v1.model.domain.PermissaoModel;
import br.com.ybardockz.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import br.com.ybardockz.domain.model.Permissao;
import br.com.ybardockz.domain.repository.PermissaoRepository;
import br.com.ybardockz.domain.service.CadastroGrupoService;
import br.com.ybardockz.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping(path = "/v1/grupo/{grupoId}/permissao",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
		cadastroGrupoService.buscarOuFalhar(grupoId);
		
		List<Permissao> permissoes = permissaoRepository.findPermissoesByGrupoId(grupoId);
		
		CollectionModel<PermissaoModel> permissoesModel = permissaoModelAssembler
				.toCollectionModel(permissoes)
				.removeLinks()
				.add(algaLinks.linkToGrupoPermissao(grupoId))
				.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
		
		permissoesModel.forEach(permissaoModel -> {
			permissaoModel.add(algaLinks
					.linkToGrupoPermissaoDisassociacao(grupoId, 
							permissaoModel.getId(), "desassociar"));
		});
		
		return permissoesModel;
	}
	
	@GetMapping("/{permissaoId}")
	public PermissaoModel buscarPorId(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		Permissao permissao = cadastroPermissaoService.buscarOuFalharPorGrupo(grupoId, permissaoId);
		
		return permissaoModelAssembler.toModel(permissao);
	}
	
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.associar(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> disassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.disassociar(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}

}