package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.controller.CidadeController;
import br.com.ybardockz.api.model.domain.CidadeModel;
import br.com.ybardockz.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {
	
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(algaLinks.linkToCidades("cidades"));
		
		cidadeModel.getEstado().add(algaLinks.linkToEstados(cidadeModel.getEstado().getId()));
		
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
