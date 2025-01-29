package br.com.ybardockz.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.RestauranteNaoEncontradoException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cozinhaService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
	
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Restaurante de código: " + id + 
					" não pode ser removido pois está em uso.");
		}
		
	}
	
	@Transactional
	public void dessasociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
		
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
		
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.ativar();
		
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.inativar();
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::ativar);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		try {
			restauranteIds.forEach(this::inativar);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.fechar();
	}
	
	@Transactional
	public void associarUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarUsuario(usuario);
	}
	
	@Transactional
	public void disassociarUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.removerUsuario(usuario);
	}

	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow
				(() -> new RestauranteNaoEncontradoException(id));
	}
	


}
