package br.com.ybardockz.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	public ProdutoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ProdutoNaoEncontradoException(Long restauranteId ,Long produtoId) {
		super("Produto de código: " + produtoId + " não foi encontrado no Restaurante de código: " + restauranteId);
	}

	private static final long serialVersionUID = 1L;

}
