package br.com.ybardockz.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private DataSize max;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.max = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if (value == null || value.getSize() <= max.toBytes()) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
