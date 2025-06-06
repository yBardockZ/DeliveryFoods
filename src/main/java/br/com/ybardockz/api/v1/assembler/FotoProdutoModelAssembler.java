package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.controller.RestauranteProdutoFotoController;
import br.com.ybardockz.api.v1.model.domain.FotoProdutoModel;
import br.com.ybardockz.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends 
	RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

	public FotoProdutoModelAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FotoProdutoModel toModel(FotoProduto foto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
		
		fotoProdutoModel.add(algaLinks
				.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));
		
		fotoProdutoModel.add(algaLinks
				.linkToProdutos(foto.getProduto().getId(), foto.getRestauranteId(),
						"produto"));
		
		return fotoProdutoModel;
	}
	
}
