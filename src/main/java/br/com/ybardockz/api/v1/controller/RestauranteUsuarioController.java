package br.com.ybardockz.api.v1.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.assembler.UsuarioModelAssembler;
import br.com.ybardockz.api.v1.model.domain.UsuarioModel;
import br.com.ybardockz.api.v1.openapi.controller.RestauranteUsuarioControllerOpenApi;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurante/{restauranteId}/responsaveis",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Collection<Usuario> usuarios = restaurante.getUsuariosResponsaveis();
		
		CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler.toCollectionModel(usuarios)
				.removeLinks()
				.add(algaLinks.linkToResponsaveis(restauranteId))
				.add(algaLinks.linkToResponsaveisAssociacao
						(restauranteId, "associar"));
		
		usuariosModel.getContent().forEach(usuarioModel -> {
			usuarioModel.add(algaLinks
					.linkToResponsaveisDesassociacao(restauranteId, restauranteId, "desassociar"));
		});
		
		return usuariosModel;
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
		restauranteService.associarUsuario(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disassociar(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
		restauranteService.disassociarUsuario(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

}
