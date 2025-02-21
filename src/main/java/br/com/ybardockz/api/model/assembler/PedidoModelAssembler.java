package br.com.ybardockz.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.controller.PedidoController;
import br.com.ybardockz.api.model.domain.PedidoModel;
import br.com.ybardockz.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}
	
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
		pedidoModel.add(algaLinks.linkToPedidos());
		
		pedidoModel.getRestaurante().add(algaLinks
				.linkToRestaurantes(pedido.getRestaurante().getId()));
		
		pedidoModel.getCliente().add(algaLinks
				.linkToUsuarios(pedidoModel.getCliente().getId()));
		
		pedidoModel.getFormaPagamento().add(algaLinks
				.linkToFormasPagamento(pedidoModel.getFormaPagamento().getId()));
		
		pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks
				.linkToCidades(pedidoModel.getEnderecoEntrega().getCidade().getId()));
		
		pedidoModel.getItens().forEach(itemPedido -> {
			itemPedido.add(algaLinks.linkToProdutos(itemPedido.getProdutoId(), pedido.getRestaurante().getId(),
					 "produtos"));
		});
		
		return pedidoModel;
	}

	/*
	public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
		List<PedidoModel> pedidosModels = pedidos.stream()
				.map((pedidoDomain) -> toModel(pedidoDomain))
				.collect(Collectors.toList());
		
		return pedidosModels;
	}
	*/

}
