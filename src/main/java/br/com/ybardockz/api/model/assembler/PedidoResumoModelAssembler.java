package br.com.ybardockz.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.PedidoResumoModel;
import br.com.ybardockz.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoResumoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoModel.class);
	}
	
	public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos) {
		List<PedidoResumoModel> pedidosResumoModels = pedidos.stream()
				.map((pedidoDomain) -> toModel(pedidoDomain))
				.collect(Collectors.toList());
		
		return pedidosResumoModels;
	}

}
