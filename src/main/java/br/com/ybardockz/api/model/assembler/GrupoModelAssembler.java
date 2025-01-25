package br.com.ybardockz.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.GrupoModel;
import br.com.ybardockz.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoModel toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoModel.class);
	}
	
	public List<GrupoModel> toCollectionModel(List<Grupo> grupos) {
		List<GrupoModel> gruposModel = grupos.stream()
				.map((grupoDomain) -> toModel(grupoDomain))
				.collect(Collectors.toList());
		
		return gruposModel;
	}
	
}
