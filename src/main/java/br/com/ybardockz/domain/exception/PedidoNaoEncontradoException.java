package br.com.ybardockz.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public PedidoNaoEncontradoException(Long pedidoId) {
		super("O pedido de código: " + pedidoId + " não foi encontrado.");
	}

}
