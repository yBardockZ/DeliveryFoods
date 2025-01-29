package br.com.ybardockz.api.model.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.PermissaoModel;
import br.com.ybardockz.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PermissaoModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class);
	}
	
	public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
		List<PermissaoModel> permissoesModel = permissoes.stream()
				.map((permissaoDomain) -> toModel(permissaoDomain))
				.collect(Collectors.toList());
		
		return permissoesModel;
	}

}
