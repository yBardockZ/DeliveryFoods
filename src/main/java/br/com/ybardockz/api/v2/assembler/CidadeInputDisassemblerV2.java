package br.com.ybardockz.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v2.model.input.CidadeInputV2;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomain(CidadeInputV2 cidadeInput, Cidade cidade) {
		// Para evitar: [org.springframework.orm.jpa.JpaSystemException: 
		//identifier of an instance of br.com.ybardockz.domain.model.Estado was altered from 2 to 1]
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInput, cidade);
	}
	
	

}
