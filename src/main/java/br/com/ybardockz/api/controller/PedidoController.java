package br.com.ybardockz.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.PedidoInputDisassembler;
import br.com.ybardockz.api.model.assembler.PedidoModelAssembler;
import br.com.ybardockz.api.model.assembler.PedidoResumoModelAssembler;
import br.com.ybardockz.api.model.domain.PedidoModel;
import br.com.ybardockz.api.model.domain.PedidoResumoModel;
import br.com.ybardockz.api.model.input.PedidoInput;
import br.com.ybardockz.core.data.PageableTranslator;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.ProdutoNaoEncontradoException;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.PedidoRepository;
import br.com.ybardockz.domain.repository.filter.PedidoFilter;
import br.com.ybardockz.domain.service.EmissaoPedidoService;
import br.com.ybardockz.infraestructure.repository.spec.PedidoSpecs;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService pedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired 
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		
		List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
				.toCollectionModel(pedidos.getContent());
		
		Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, 
				pageable, pedidos.getTotalElements());

		return pedidosResumoModelPage;
	}
	
	@GetMapping("/{pedidoCodigo}")
	public PedidoModel buscarPorId(@PathVariable String pedidoCodigo) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoCodigo);
		
		return pedidoModelAssembler.toModel(pedido);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
		Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(1L);
		
		pedido = pedidoService.emitir(pedido);
		
		return pedidoModelAssembler.toModel(pedido);
		} catch (ProdutoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		Map<String, String> campos = Map.of(
				"codigo", "codigo",
				"valoTotal", "valorTotal",
				"subTotal", "subTotal",
				"taxaFrete", "taxaFrete",
				"dataCriacao", "dataCriacao",
				"status", "status",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome"
				);
		
		return PageableTranslator.translatePage(apiPageable, campos);
	}

}
