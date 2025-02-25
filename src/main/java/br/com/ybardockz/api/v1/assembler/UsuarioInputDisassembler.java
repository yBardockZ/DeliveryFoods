package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.model.input.UsuarioComSenhaInput;
import br.com.ybardockz.api.v1.model.input.UsuarioInput;
import br.com.ybardockz.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Usuario usuarioComSenhatoDomainObject(UsuarioComSenhaInput usuarioComSenhaInput) {
		return modelMapper.map(usuarioComSenhaInput, Usuario.class);
	}
	
	public Usuario usuarioInputToDomainObject(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomain(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
}
