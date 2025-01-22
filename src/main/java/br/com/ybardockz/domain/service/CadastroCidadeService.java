package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.CidadeNaoEncontradaException;
import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.Estado;
import br.com.ybardockz.domain.repository.CidadeRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade de código: " + id + 
					" não pode ser removida pois está em uso.");
		}
	}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id)
			.orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

}
