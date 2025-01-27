package br.com.ybardockz.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public UsuarioNaoEncontradoException(Long id) {
		super("O usuário de código: " + id + " não pode ser encontrado.");
	}

	private static final long serialVersionUID = 1L;

}
