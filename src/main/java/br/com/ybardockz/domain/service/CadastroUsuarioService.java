package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.UsuarioNaoEncontradoException;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public void trocarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
		Usuario usuarioAtual = buscarOuFalhar(usuarioId);
		
		if (!usuarioAtual.senhaCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuarioAtual.atualizarSenha(senhaNova);
		
		salvar(usuarioAtual);
		
	}

}
