package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.UsuarioController;
import br.com.ybardockz.api.controller.UsuarioGrupoController;
import br.com.ybardockz.api.model.domain.UsuarioModel;
import br.com.ybardockz.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	@Override
	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);
		
		usuarioModel.add(linkTo(UsuarioController.class).withRel("usuarios"));
		usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioModel.getId()))
				.withRel("grupos"));
		
		return usuarioModel;
	}
	
	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
				.add(linkTo(UsuarioController.class).withSelfRel());
	}
	
	/*
	public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
		List<UsuarioModel> usuariosModels = usuarios.stream() 
				.map((usuarioDomain) -> toModel(usuarioDomain))
				.collect(Collectors.toList());
		
		return usuariosModels;
	}
	*/
	

}
