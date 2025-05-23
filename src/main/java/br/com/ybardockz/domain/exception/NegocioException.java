package br.com.ybardockz.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NegocioException(String msg) {
		super(msg);
	}
	
	public NegocioException(String msg, Throwable causa) {
		super(msg, causa);
	}

}
