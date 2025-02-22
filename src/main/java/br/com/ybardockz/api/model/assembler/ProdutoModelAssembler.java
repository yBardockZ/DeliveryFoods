package br.com.ybardockz.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.controller.RestauranteProdutoController;
import br.com.ybardockz.api.model.domain.ProdutoModel;
import br.com.ybardockz.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, 
	ProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public ProdutoModelAssembler() {
		super(RestauranteProdutoController.class, ProdutoModel.class);
	}
	
	public ProdutoModel toModel(Produto produto) {
		ProdutoModel produtoModel = createModelWithId(produto.getId(), produto,
				 produto.getRestaurante().getId());
		
		modelMapper.map(produto, produtoModel);
		
		produtoModel.add(algaLinks
				.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
		
		produtoModel.add(algaLinks
				.linkToFotoProduto(produto.getRestaurante().getId(),
						produto.getId(), "fotos"));
		
		return produtoModel;
	}
	
	/*
	public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
		List<ProdutoModel> produtosModel = produtos.stream()
				.map((produtoDomain) -> toModel(produtoDomain))
				.collect(Collectors.toList());
		
		return produtosModel;
	}
	*/

}
