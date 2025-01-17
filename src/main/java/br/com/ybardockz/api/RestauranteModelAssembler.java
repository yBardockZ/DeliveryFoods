package br.com.ybardockz.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.CozinhaModel;
import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = new RestauranteModel();
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		
		CozinhaModel cozinhaModel = new CozinhaModel();
		cozinhaModel.setId(restaurante.getCozinha().getId());
		cozinhaModel.setNome(restaurante.getCozinha().getNome());
		
		restauranteModel.setCozinha(cozinhaModel);
		
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
