package br.com.ybardockz.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CozinhaNaoEncontradaException(Long id) {
		this("Cozinha de código : " + id + " não foi encontrada.");
	}

}
