package br.com.ybardockz.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.controller.GrupoController;
import br.com.ybardockz.api.model.domain.GrupoModel;
import br.com.ybardockz.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends 
	RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}
	
	public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);
		
		grupoModel.add(algaLinks.linkToGrupos("grupos"));
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToGrupos());
	}
	
	/*
	public List<GrupoModel> toCollectionModel(List<Grupo> grupos) {
		List<GrupoModel> gruposModel = grupos.stream()
				.map((grupoDomain) -> toModel(grupoDomain))
				.collect(Collectors.toList());
		
		return gruposModel;
	}
	*/
	
}
