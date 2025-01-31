package br.com.ybardockz.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import br.com.ybardockz.api.model.assembler.PedidoInputDisassembler;
import br.com.ybardockz.api.model.assembler.PedidoModelAssembler;
import br.com.ybardockz.api.model.assembler.PedidoResumoModelAssembler;
import br.com.ybardockz.api.model.domain.PedidoModel;
import br.com.ybardockz.api.model.domain.PedidoResumoModel;
import br.com.ybardockz.api.model.input.PedidoInput;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.exception.ProdutoNaoEncontradoException;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.Usuario;
import br.com.ybardockz.domain.repository.PedidoRepository;
import br.com.ybardockz.domain.service.EmissaoPedidoService;
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
	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		List<PedidoResumoModel> pedidosResumoModel = 
				pedidoResumoModelAssembler.toCollectionModel(pedidos);
		
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumoModel);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if (StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", 
					SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		pedidosWrapper.setFilters(filterProvider);
		
		return pedidosWrapper;
		
	}
	
	
// @GetMapping
//	public List<PedidoResumoModel> listar() {
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		
//		return pedidoResumoModelAssembler.toCollectionModel(pedidos);
//	}
	
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

}
