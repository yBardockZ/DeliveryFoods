package br.com.ybardockz.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	public GrupoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public GrupoNaoEncontradoException(Long id) {
		super("O grupo de código: " + id + " não foi encontrado.");
	}

	private static final long serialVersionUID = 1L;

}
