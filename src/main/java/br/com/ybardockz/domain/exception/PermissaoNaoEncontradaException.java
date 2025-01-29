package br.com.ybardockz.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public PermissaoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public PermissaoNaoEncontradaException(Long permissaoId) {
		super("A permissão de código: " + permissaoId + " não foi encontrada.");
	}
	
	public PermissaoNaoEncontradaException(Long grupoId, Long permissaoId) {
		super("A permissão de código: " + permissaoId + " não foi encontrada"
				+ " no grupo de código: " + grupoId);
	}

}
