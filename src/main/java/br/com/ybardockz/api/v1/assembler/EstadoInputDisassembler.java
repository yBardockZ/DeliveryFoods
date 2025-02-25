package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.model.input.EstadoInput;
import br.com.ybardockz.domain.model.Estado;

@Component
public class EstadoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	public void copyToDomain(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}

}
