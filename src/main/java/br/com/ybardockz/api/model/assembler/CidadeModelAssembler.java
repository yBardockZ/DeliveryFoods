package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.CidadeController;
import br.com.ybardockz.api.controller.EstadoController;
import br.com.ybardockz.api.model.domain.CidadeModel;
import br.com.ybardockz.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {
	
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);
		
		//CidadeModel cidadeModel = modelMapper.map(cidade, CidadeModel.class);
		
		//cidadeModel.add(linkTo(CidadeController.class).slash(cidadeModel.getId()).withSelfRel());
		
		cidadeModel.add(linkTo(CidadeController.class).withRel("cozinhas"));
		
		cidadeModel.getEstado().add(linkTo(EstadoController.class)
				.slash(cidadeModel.getEstado().getId()).withSelfRel());
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CidadeController.class).withSelfRel());
	}
	
	/*
	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
		List<CidadeModel> cidadesModel = cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
		
		return cidadesModel;
	}
	*/
	
}
