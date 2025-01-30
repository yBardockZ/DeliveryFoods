package br.com.ybardockz.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String pedidoCodigo) {
		super("O pedido de código: " + pedidoCodigo + " não foi encontrado.");
	}

}
