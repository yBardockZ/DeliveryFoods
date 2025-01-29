package br.com.ybardockz.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.UsuarioNaoEncontradoException;
import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService grupoService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException("Já existe um usuário cadastro com o e-mail: "
					+ usuario.getEmail());
		}
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	@Transactional
	public void trocarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
		Usuario usuarioAtual = buscarOuFalhar(usuarioId);
		
		if (!usuarioAtual.senhaCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuarioAtual.atualizarSenha(senhaNova);
		
		salvar(usuarioAtual);
		
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	@Transactional
	public void disassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
		
	}
}
