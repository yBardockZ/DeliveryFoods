package br.com.ybardockz.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.controller.RestauranteController;
import br.com.ybardockz.api.model.domain.RestauranteBasicoModel;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler extends 
	RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModel.class);
	}

	@Override
	public RestauranteBasicoModel toModel(Restaurante restaurante) {
		RestauranteBasicoModel restauranteModel = 
				createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteModel.getCozinha().add(algaLinks
				.linkToCozinhas(restauranteModel.getCozinha().getId()));
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}

}
