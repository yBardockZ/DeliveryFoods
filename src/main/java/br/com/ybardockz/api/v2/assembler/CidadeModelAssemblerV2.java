package br.com.ybardockz.api.v2.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v2.AlgaLinksV2;
import br.com.ybardockz.api.v2.controller.CidadeControllerV2;
import br.com.ybardockz.api.v2.model.CidadeModelV2;
import br.com.ybardockz.domain.model.Cidade;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {
	
	public CidadeModelAssemblerV2() {
		super(CidadeControllerV2.class, CidadeModelV2.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	@Override
	public CidadeModelV2 toModel(Cidade cidade) {
		CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(algaLinks.linkToCidades("cidades"));
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CidadeControllerV2.class).withSelfRel());
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
