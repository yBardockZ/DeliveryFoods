package br.com.ybardockz.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	public EstadoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradoException(Long id) {
		this("O estado de id: " + id + " não foi encontrado.");
	}

	private static final long serialVersionUID = 1L;

}
