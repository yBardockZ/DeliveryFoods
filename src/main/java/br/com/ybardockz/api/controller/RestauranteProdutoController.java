package br.com.ybardockz.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.ProdutoInputDisassembler;
import br.com.ybardockz.api.model.assembler.ProdutoModelAssembler;
import br.com.ybardockz.api.model.domain.ProdutoModel;
import br.com.ybardockz.api.model.input.ProdutoInput;
import br.com.ybardockz.domain.model.Produto;
import br.com.ybardockz.domain.repository.ProdutoRepository;
import br.com.ybardockz.domain.service.CadastroProdutoService;
import br.com.ybardockz.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produto")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		restauranteService.buscarOuFalhar(restauranteId);
		
		List<Produto> produtos = new ArrayList<>();
		
		if (incluirInativos) {
			produtos = produtoRepository.findProdutosByRestauranteId(restauranteId);
		}
		else {
			produtos = produtoRepository.findProdutosAtivosByRestauranteId(restauranteId);
		}
		
		return produtoModelAssembler
				.toCollectionModel(produtos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoModel buscarPorId(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		Produto produto = produtoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		
		return produtoModelAssembler.toModel(produto);
	}
	
	@PostMapping
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoDomain = produtoInputDisassembler.toDomainObject(produtoInput);
		produtoDomain.setRestaurante(restauranteService.buscarOuFalhar(restauranteId));
		produtoDomain = produtoService.salvar(produtoDomain);
		
		return produtoModelAssembler.toModel(produtoDomain);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		restauranteService.buscarOuFalhar(produtoId);
		Produto produtoDomain = produtoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToDomain(produtoInput, produtoDomain);
		produtoDomain = produtoService.salvar(produtoDomain);
		
		return produtoModelAssembler.toModel(produtoDomain);
	}

}
