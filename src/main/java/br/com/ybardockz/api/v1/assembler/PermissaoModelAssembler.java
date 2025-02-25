package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.controller.GrupoPermissaoController;
import br.com.ybardockz.api.v1.model.domain.PermissaoModel;
import br.com.ybardockz.domain.model.Permissao;

@Component
public class PermissaoModelAssembler extends 
	RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PermissaoModelAssembler() {
		super(GrupoPermissaoController.class, PermissaoModel.class);
	}
	
	public PermissaoModel toModel(Permissao permissao) {
		PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
		
		return permissaoModel;
	}
	
	@Override
	public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToPermissoes());
	}
	
	/*
	public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
		List<PermissaoModel> permissoesModel = permissoes.stream()
				.map((permissaoDomain) -> toModel(permissaoDomain))
				.collect(Collectors.toList());
		
		return permissoesModel;
	}
	*/

}
