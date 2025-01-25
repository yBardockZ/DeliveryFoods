package br.com.ybardockz.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.input.RestauranteInput;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = modelMapper.map(restauranteInput, Restaurante.class);
		
		return restaurante;
	}
	
	public void copyToDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar: org.springframework.orm.jpa.JpaSystemException
		restaurante.setCozinha(new Cozinha());
		
		if (restaurante.getEndereco() != null ) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		
		modelMapper.map(restauranteInput, restaurante);
	}

}