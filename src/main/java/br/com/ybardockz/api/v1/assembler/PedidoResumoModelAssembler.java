package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.controller.PedidoController;
import br.com.ybardockz.api.v1.model.domain.PedidoResumoModel;
import br.com.ybardockz.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}
	
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		 modelMapper.map(pedido, pedidoResumoModel);
		 
		 pedidoResumoModel.add(algaLinks.linkToPedidos("pedidos"));
		 
		 pedidoResumoModel.getRestaurante().add(algaLinks
				 .linkToRestaurantes(pedidoResumoModel.getRestaurante().getId()));
		 
		 pedidoResumoModel.getCliente().add(algaLinks
				 .linkToUsuarios(pedidoResumoModel.getCliente().getId()));
		 
		 return pedidoResumoModel;
	}
	
	@Override
	public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToPedidos("pedidos"));
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
