package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.model.input.CidadeInput;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.Estado;

@Component
public class CidadeInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomain(CidadeInput cidadeInput, Cidade cidade) {
		// Para evitar: [org.springframework.orm.jpa.JpaSystemException: 
		//identifier of an instance of br.com.ybardockz.domain.model.Estado was altered from 2 to 1]
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInput, cidade);
	}
	
	

}
