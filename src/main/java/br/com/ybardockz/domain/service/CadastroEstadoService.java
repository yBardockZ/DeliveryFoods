package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.EstadoNaoEncontradoException;
import br.com.ybardockz.domain.model.Estado;
import br.com.ybardockz.domain.repository.EstadoRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
		
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			
			estadoRepository.deleteById(id);
			estadoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Estado de código: " + id + 
					" não pode ser removida pois está em uso.");
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

}
