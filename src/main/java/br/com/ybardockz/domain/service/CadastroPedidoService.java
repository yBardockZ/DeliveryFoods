package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.PedidoNaoEncontradoException;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.PedidoRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		Long restauranteId = pedido.getRestaurante().getId();
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		Long usuarioId = pedido.getCliente().getId();
		Usuario cliente = usuarioService.buscarOuFalhar(usuarioId);
		
		Long formaPagamentoId = pedido.getFormaPagamento().getId();
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		pedido.setRestaurante(restaurante);
		pedido.setCliente(cliente);
		pedido.setFormaPagamento(formaPagamento);
		
		return pedidoRepository.save(pedido);
	}

}
