package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.model.input.ProdutoInput;
import br.com.ybardockz.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	public void copyToDomain(ProdutoInput produtoInput, Produto produto) {
		modelMapper.map(produtoInput, produto);
	}

}
