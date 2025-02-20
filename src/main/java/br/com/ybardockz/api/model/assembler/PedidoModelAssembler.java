package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.CidadeController;
import br.com.ybardockz.api.controller.FormaPagamentoController;
import br.com.ybardockz.api.controller.PedidoController;
import br.com.ybardockz.api.controller.RestauranteController;
import br.com.ybardockz.api.controller.RestauranteProdutoController;
import br.com.ybardockz.api.controller.UsuarioController;
import br.com.ybardockz.api.model.domain.PedidoModel;
import br.com.ybardockz.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}
	
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
		pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		
		pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
				.buscarPorId(pedidoModel.getRestaurante().getId())).withSelfRel());
		
		pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
				.buscarPorId(pedidoModel.getCliente().getId())).withSelfRel());
		
		pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
				.buscarPorId(pedidoModel.getFormaPagamento().getId(), null)).withSelfRel());
		
		pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
				.buscarPorId(pedidoModel.getEnderecoEntrega().getCidade().getId())).withSelfRel());
		
		pedidoModel.getItens().forEach(itemPedido -> {
			itemPedido.add(linkTo(methodOn(RestauranteProdutoController.class)
					.buscarPorId(itemPedido.getProdutoId(), pedidoModel.getRestaurante().getId()))
					.withRel("produto"));
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
