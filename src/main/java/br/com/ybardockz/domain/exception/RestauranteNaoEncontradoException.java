package br.com.ybardockz.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String msg) {
		super(msg);
	}

	public RestauranteNaoEncontradoException(Long id) {
		this("O restaurante de id: " + id + " não foi encontrado.");
	}

}
