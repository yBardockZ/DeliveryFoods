package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.FormaPagamentoNaoEncontradaException;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.repository.FormaPagamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroFormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("A forma de pagamento de código: " + id +
					" não pode ser removida pois está em uso.");
		}
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

}
