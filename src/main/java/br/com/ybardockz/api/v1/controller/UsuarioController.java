package br.com.ybardockz.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.assembler.UsuarioInputDisassembler;
import br.com.ybardockz.api.v1.assembler.UsuarioModelAssembler;
import br.com.ybardockz.api.v1.model.domain.UsuarioModel;
import br.com.ybardockz.api.v1.model.input.SenhaInput;
import br.com.ybardockz.api.v1.model.input.UsuarioComSenhaInput;
import br.com.ybardockz.api.v1.model.input.UsuarioInput;
import br.com.ybardockz.api.v1.openapi.controller.UsuarioControllerOpenApi;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.UsuarioRepository;
import br.com.ybardockz.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path = "/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {
	
	@Autowired
	private CadastroUsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		List<Usuario> usuarios = repository.findAll();
		
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
		Usuario usuario = service.buscarOuFalhar(usuarioId);
		
		return usuarioModelAssembler.toModel(usuario);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
		Usuario usuarioDomain = usuarioInputDisassembler.usuarioComSenhatoDomainObject(usuarioComSenhaInput);
		usuarioDomain = service.salvar(usuarioDomain);
		
		return usuarioModelAssembler.toModel(usuarioDomain);
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@RequestBody @Valid UsuarioInput usuarioInput,
			@PathVariable Long usuarioId) {
		Usuario usuarioDomain = service.buscarOuFalhar(usuarioId);
		usuarioInputDisassembler.copyToDomain(usuarioInput, usuarioDomain);
		usuarioDomain = service.salvar(usuarioDomain);
		
		return usuarioModelAssembler.toModel(usuarioDomain);
		
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void trocarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhas) {
		service.trocarSenha(usuarioId, senhas.getSenhaAtual(), senhas.getSenhaNova());
		
		
	}

}
