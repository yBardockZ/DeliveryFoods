package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.GrupoNaoEncontradoException;
import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.repository.GrupoRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroGrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
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
	
}
