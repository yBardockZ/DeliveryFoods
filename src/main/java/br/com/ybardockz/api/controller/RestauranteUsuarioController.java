package br.com.ybardockz.api.controller;

import java.util.Collection;
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

import br.com.ybardockz.api.model.assembler.UsuarioModelAssembler;
import br.com.ybardockz.api.model.domain.UsuarioModel;
import br.com.ybardockz.api.openapi.controller.RestauranteUsuarioControllerOpenApi;
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
	
	@GetMapping
	public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Collection<Usuario> usuarios = restaurante.getUsuariosResponsaveis();
		
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
		restauranteService.associarUsuario(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociar(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
		restauranteService.disassociarUsuario(restauranteId, usuarioId);
	}

}
