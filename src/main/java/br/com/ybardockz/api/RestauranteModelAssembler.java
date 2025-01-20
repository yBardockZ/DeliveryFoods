package br.com.ybardockz.api;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = modelMapper.map(restaurante, RestauranteModel.class);
		
		return restauranteModel;
	}
	
	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		List<RestauranteModel> restaurantesModel = new ArrayList<>();
		
		for (Restaurante r : restaurantes) {
			restaurantesModel.add(toModel(r));
		}
		
		return restaurantesModel;
		
	}

}
