package br.com.ybardockz.api.v1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.assembler.PedidoInputDisassembler;
import br.com.ybardockz.api.v1.assembler.PedidoModelAssembler;
import br.com.ybardockz.api.v1.assembler.PedidoResumoModelAssembler;
import br.com.ybardockz.api.v1.model.domain.PedidoModel;
import br.com.ybardockz.api.v1.model.domain.PedidoResumoModel;
import br.com.ybardockz.api.v1.model.input.PedidoInput;
import br.com.ybardockz.api.v1.openapi.controller.PedidoControllerOpenApi;
import br.com.ybardockz.core.data.PageWrapper;
import br.com.ybardockz.core.data.PageableTranslator;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.ProdutoNaoEncontradoException;
import br.com.ybardockz.domain.filter.PedidoFilter;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.PedidoRepository;
import br.com.ybardockz.domain.service.EmissaoPedidoService;
import br.com.ybardockz.infraestructure.repository.spec.PedidoSpecs;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/pedido",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {
	
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
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
		
		pedidos = new PageWrapper<>(pedidos, pageable);
		
		PagedModel<PedidoResumoModel> pedidoPagedModel = pagedResourcesAssembler
				.toModel(pedidos, pedidoResumoModelAssembler);
		
		return pedidoPagedModel;
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
