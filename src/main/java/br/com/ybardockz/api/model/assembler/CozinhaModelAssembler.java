package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.CozinhaController;
import br.com.ybardockz.api.model.domain.CozinhaModel;
import br.com.ybardockz.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}
	
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
	
		cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
		
		return cozinhaModel;
		
	}
	
	@Override
	public CollectionModel<CozinhaModel> toCollectionModel(Iterable<? extends Cozinha> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CozinhaController.class).withSelfRel());
	}
	
	/*
	public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
		
		List<CozinhaModel> cozinhasModel = cozinhas.stream()
				.map(cozinha -> toModel(cozinha))
				.collect(Collectors.toList());
		
		return cozinhasModel;
	}
	*/
}
