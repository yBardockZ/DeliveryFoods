package br.com.ybardockz.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v2.model.input.CozinhaInputV2;
import br.com.ybardockz.domain.model.Cozinha;

@Component
public class CozinhaInputDisassemblerV2 {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInputV2 cozinhaInput) {
		Cozinha cozinha = modelMapper.map(cozinhaInput, Cozinha.class);
		
		return cozinha;
	}
	
	public void copyToDomain(CozinhaInputV2 cozinhaInput, Cozinha cozinha) {
		modelMapper.map(cozinhaInput, cozinha);
		
	}

}
