package br.com.ybardockz.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.controller.RestauranteController;
import br.com.ybardockz.api.model.domain.RestauranteModel;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends 
	RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(algaLinks
				.linkToRestaurantes("restaurantes"));
		
		restauranteModel.add(algaLinks
				.linkToRestauranteFormaPagamento(restauranteModel.getId(), "formas-pagamento"));
		
		restauranteModel.add(algaLinks
				.linkToResponsaveis(restaurante.getId(), "responsaveis"));
		
		if (restaurante.podeAtivar()) {
			restauranteModel.add(algaLinks
					.linkToAtivacaoRestaurante(restauranteModel.getId(), "ativar"));
		} else {
			restauranteModel.add(algaLinks
					.linkToDesativacaoRestaurante(restauranteModel.getId(), "desativar"));
		}
		
		if (restaurante.podeAbrir()) {
			restauranteModel.add(algaLinks
					.linkToAberturaRestaurante(restauranteModel.getId(), "abrir"));
		} else {
			restauranteModel.add(algaLinks
					.linkToFechamentoRestaurante(restauranteModel.getId(), "fechar"));
		}
		
		restauranteModel.getCozinha().add(algaLinks
				.linkToCozinhas(restauranteModel.getCozinha().getId()));
		
		restauranteModel.getEndereco().getCidade().add(algaLinks
				.linkToCidades(restauranteModel.getEndereco().getCidade().getId()));
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}
	
	/*
	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		List<RestauranteModel> restaurantesModel = new ArrayList<>();
		
		for (Restaurante r : restaurantes) {
			restaurantesModel.add(toModel(r));
		}
		
		return restaurantesModel;
		
	}
	*/
}
