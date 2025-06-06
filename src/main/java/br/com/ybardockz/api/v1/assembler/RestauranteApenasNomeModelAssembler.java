package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.controller.RestauranteController;
import br.com.ybardockz.api.v1.model.domain.RestauranteApenasNomeModel;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler extends 
	RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}

	@Override
	public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
		RestauranteApenasNomeModel restauranteModel = 
				createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}

}
