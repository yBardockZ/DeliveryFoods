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
import br.com.ybardockz.api.v1.assembler.GrupoModelAssembler;
import br.com.ybardockz.api.v1.model.domain.GrupoModel;
import br.com.ybardockz.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.repository.GrupoRepository;
import br.com.ybardockz.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/v1/usuario/{usuarioId}/grupo",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;

	@GetMapping
	public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
		cadastroUsuarioService.buscarOuFalhar(usuarioId);
		List<Grupo> grupos = grupoRepository.findGrupoByUsuario(usuarioId);
		
		CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(grupos)
				.removeLinks()
				.add(algaLinks.linkToUsuariosGrupos(usuarioId))
				.add(algaLinks.linkToUsuarioGruposAssociacao(usuarioId, "associar"));
		
		gruposModel.forEach(grupoModel -> {
			grupoModel.add(algaLinks
					.linkToUsuarioGruposDessasociacao(usuarioId, 
							grupoModel.getId(), "desassociar"));
		});
		
		return gruposModel;
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> disassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.disassociarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}

}
