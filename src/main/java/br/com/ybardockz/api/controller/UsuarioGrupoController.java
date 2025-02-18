package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.GrupoModelAssembler;
import br.com.ybardockz.api.model.domain.GrupoModel;
import br.com.ybardockz.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.repository.GrupoRepository;
import br.com.ybardockz.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuario/{usuarioId}/grupo",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long usuarioId) {
		cadastroUsuarioService.buscarOuFalhar(usuarioId);
		List<Grupo> grupos = grupoRepository.findGrupoByUsuario(usuarioId);
		
		return grupoModelAssembler.toCollectionModel(grupos);
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
		
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.disassociarGrupo(usuarioId, grupoId);
	}

}
