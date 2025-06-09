package br.com.ybardockz.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	public @interface Cozinhas {
		
		@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
		@Target({ ElementType.METHOD, ElementType.TYPE })
		@Retention(RetentionPolicy.RUNTIME)
		public @interface PodeEditar { }
		
		@PreAuthorize("hasAuthority('CONSULTAR_COZINHAS')")
		@Target({ ElementType.METHOD, ElementType.TYPE })
		@Retention(RetentionPolicy.RUNTIME)
		public @interface PodeConsultar { }

		
	}

}
