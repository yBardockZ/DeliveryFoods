package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.PedidoController;
import br.com.ybardockz.api.controller.RestauranteController;
import br.com.ybardockz.api.controller.UsuarioController;
import br.com.ybardockz.api.model.domain.PedidoResumoModel;
import br.com.ybardockz.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}
	
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		 modelMapper.map(pedido, pedidoResumoModel);
		 
		 pedidoResumoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		 
		 pedidoResumoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
				 .buscarPorId(pedido.getRestaurante().getId())).withSelfRel());
		 
		 pedidoResumoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
				 .buscarPorId(pedido.getCliente().getId())).withSelfRel());
		 
		 return pedidoResumoModel;
	}
	
	@Override
	public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(PedidoController.class).withRel("pedidos"));
	}
	
	/*
	public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos) {
		List<PedidoResumoModel> pedidosResumoModels = pedidos.stream()
				.map((pedidoDomain) -> toModel(pedidoDomain))
				.collect(Collectors.toList());
		
		return pedidosResumoModels;
	}
	*/
}
