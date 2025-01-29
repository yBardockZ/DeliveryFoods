package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.GrupoNaoEncontradoException;
import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.model.Permissao;
import br.com.ybardockz.domain.repository.GrupoRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroGrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Lazy
	@Autowired
	private CadastroPermissaoService permissaoService;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			grupoRepository.deleteById(id);
			grupoRepository.flush();
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Grupo de código: " + id + " não pode ser"
					+" removido pois está em uso.");
		}
	}
	
	public Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
		
	}
	
	@Transactional
	public void associar(Long grupoId, Long permissaoId) {
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		Grupo grupo = buscarOuFalhar(grupoId);
		
		grupo.associarPermissao(permissao);
	}
	
	@Transactional
	public void disassociar(Long grupoId, Long permissaoId) {
		permissaoService.buscarOuFalhar(permissaoId);
		Permissao permissao = permissaoService.buscarOuFalharPorGrupo(grupoId, permissaoId);
		Grupo grupo = buscarOuFalhar(grupoId);
		
		grupo.disassociarPermissao(permissao);
		
	}
	
}
