package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.PedidoNaoEncontradoException;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.model.ItemPedido;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.Produto;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.PedidoRepository;
import jakarta.transaction.Transactional;

@Service
public class EmissaoPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	public Pedido buscarOuFalhar(String codigo) {
		return pedidoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItems(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calculoValorTotal();
		
		return pedidoRepository.save(pedido);
	}
	
	public void validarPedido(Pedido pedido) {
		Long restauranteId = pedido.getRestaurante().getId();
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		Long clienteId = pedido.getCliente().getId();
		Usuario cliente = usuarioService.buscarOuFalhar(clienteId);
		
		Long formaPagamentoId = pedido.getFormaPagamento().getId();
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		pedido.setRestaurante(restaurante);
		pedido.setCliente(cliente);
		pedido.setFormaPagamento(formaPagamento);
		pedido.getEnderecoEntrega().setCidade(cidade);
		
		if (!pedido.getRestaurante().aceitaFormaDePagamento(formaPagamento)) {
			throw new NegocioException("Forma de pagamento: " + formaPagamento.getDescricao() +
					" não é aceita no restaurante.");
		}
	}
	
	public void validarItems(Pedido pedido) {
		for (ItemPedido item : pedido.getItens()) {
			Produto produto = produtoService.buscarProdutoDoRestaurante(pedido.getRestaurante().getId(),
			item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		}
		
	}

}
