package br.com.ybardockz.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.UsuarioModel;
import br.com.ybardockz.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioModel toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioModel.class);
	}
	
	public List<UsuarioModel> toCollectionModel(List<Usuario> usuarios) {
		List<UsuarioModel> usuariosModels = usuarios.stream()
				.map((usuarioDomain) -> toModel(usuarioDomain))
				.collect(Collectors.toList());
		
		return usuariosModels;
	}

}
