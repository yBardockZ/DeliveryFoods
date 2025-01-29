package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.PedidoModelAssembler;
import br.com.ybardockz.api.model.domain.PedidoModel;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.repository.PedidoRepository;
import br.com.ybardockz.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroPedidoService pedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@GetMapping
	public List<PedidoModel> listar() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		return pedidoModelAssembler.toCollectionModel(pedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoModel buscarPorId(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
		
	}

}
