package br.com.ybardockz.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	public FormaPagamentoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public FormaPagamentoNaoEncontradaException(Long id) {
		super("Forma de pagamento de código: " + id + " não foi encontrada.");
	}

	private static final long serialVersionUID = 1L;

}
