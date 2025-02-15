package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.exceptionhandler.Problema;
import br.com.ybardockz.api.model.assembler.UsuarioInputDisassembler;
import br.com.ybardockz.api.model.assembler.UsuarioModelAssembler;
import br.com.ybardockz.api.model.domain.UsuarioModel;
import br.com.ybardockz.api.model.input.SenhaInput;
import br.com.ybardockz.api.model.input.UsuarioComSenhaInput;
import br.com.ybardockz.api.model.input.UsuarioInput;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.UsuarioRepository;
import br.com.ybardockz.domain.service.CadastroUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Usuários", description = "Gerencia os usuários")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private CadastroUsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@Operation(summary = "Lista os usuários")
	@GetMapping
	public List<UsuarioModel> listar() {
		List<Usuario> usuarios = repository.findAll();
		
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}
	
	@Operation(summary = "Busca um usuário")
	@GetMapping("/{usuarioId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Id inválido.",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = Problema.class))),
			
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = Problema.class)))
		})
	public UsuarioModel buscarPorId(@Parameter(description = "ID do usuário", required = true)
		@PathVariable Long usuarioId) {
		Usuario usuario = service.buscarOuFalhar(usuarioId);
		
		return usuarioModelAssembler.toModel(usuario);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuário cadastrado",
					content = @Content(mediaType = "application/json"
							,schema = @Schema(implementation = UsuarioModel.class)))
	})
	@Operation(summary = "Registra um usuário")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(
			@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
		Usuario usuarioDomain = usuarioInputDisassembler.usuarioComSenhatoDomainObject(usuarioComSenhaInput);
		usuarioDomain = service.salvar(usuarioDomain);
		
		return usuarioModelAssembler.toModel(usuarioDomain);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado"),
			
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado",
			content = @Content(mediaType = "application/json"))
	})
	@Operation(summary = "Atualiza um usuário")
	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(
			@RequestBody @Valid UsuarioInput usuarioInput,
			@Parameter(description = "ID do usuário", required = true)
			@PathVariable Long usuarioId) {
		Usuario usuarioDomain = service.buscarOuFalhar(usuarioId);
		usuarioInputDisassembler.copyToDomain(usuarioInput, usuarioDomain);
		usuarioDomain = service.salvar(usuarioDomain);
		
		return usuarioModelAssembler.toModel(usuarioDomain);
		
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado",
			content = @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Problema.class)))
	})
	@Operation(summary = "Troca senha de um usuário")
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void trocarSenha(
			@Parameter(description = "ID do usuário", required = true)
			@PathVariable Long usuarioId, 
			@RequestBody @Valid SenhaInput senhas) {
		service.trocarSenha(usuarioId, senhas.getSenhaAtual(), senhas.getSenhaNova());
		
		
	}

}
