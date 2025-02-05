package br.com.ybardockz.core.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowed;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowed = new ArrayList<>(Arrays.asList(constraintAnnotation.allowed()));
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if (value == null || allowed.contains(value.getContentType())) {
			return true;
		}
		
		return false;
	}

}
