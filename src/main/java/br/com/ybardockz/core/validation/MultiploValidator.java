package br.com.ybardockz.core.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {
	
	private Integer numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo = constraintAnnotation.numero();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if (value != null) {
			
			BigDecimal valorDecimal = BigDecimal.valueOf(value.doubleValue());
			BigDecimal multiploDecimal = BigDecimal.valueOf(numeroMultiplo.doubleValue());
			BigDecimal resto = multiploDecimal.remainder(valorDecimal);
			
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
			
		}
		
		return valido;
	}

}
