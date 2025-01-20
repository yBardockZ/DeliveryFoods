package br.com.ybardockz.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.input.RestauranteInput;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = modelMapper.map(restauranteInput, Restaurante.class);
		
		return restaurante;
	}

}