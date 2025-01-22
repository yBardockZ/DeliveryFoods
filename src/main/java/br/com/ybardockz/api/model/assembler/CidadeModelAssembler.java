package br.com.ybardockz.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.CidadeModel;
import br.com.ybardockz.domain.model.Cidade;

@Component
public class CidadeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeModel toModel(Cidade cidade) {
		return modelMapper.map(cidade, CidadeModel.class);
	}
	
	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
		List<CidadeModel> cidadesModel = cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
		
		return cidadesModel;
	}
	
}
