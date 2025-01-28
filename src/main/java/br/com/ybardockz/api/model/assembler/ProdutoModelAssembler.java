package br.com.ybardockz.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.ProdutoModel;
import br.com.ybardockz.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}
	
	public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
		List<ProdutoModel> produtosModel = produtos.stream()
				.map((produtoDomain) -> toModel(produtoDomain))
				.collect(Collectors.toList());
		
		return produtosModel;
	}

}
