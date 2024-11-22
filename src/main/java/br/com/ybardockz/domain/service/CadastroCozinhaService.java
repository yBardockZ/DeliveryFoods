package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.CozinhaNaoEncontradaException;
import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			cozinhaRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cozinha de código: " + id + " não pode ser removida pois está em uso.");
		}
	}
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public Cozinha buscarOuFalhar(Long id) {
		return cozinhaRepository.findById(id)
		.orElseThrow(() -> new CozinhaNaoEncontradaException(id));
		
	}

}
