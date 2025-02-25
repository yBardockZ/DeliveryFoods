package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.model.input.CozinhaInput;
import br.com.ybardockz.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		Cozinha cozinha = modelMapper.map(cozinhaInput, Cozinha.class);
		
		return cozinha;
	}
	
	public void copyToDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
		modelMapper.map(cozinhaInput, cozinha);
		
	}

}
