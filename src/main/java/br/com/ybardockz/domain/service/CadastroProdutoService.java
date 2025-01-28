package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.ProdutoNaoEncontradoException;
import br.com.ybardockz.domain.model.Produto;
import br.com.ybardockz.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class CadastroProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	public Produto buscarProdutoDoRestaurante(Long restauranteId, Long produtoId) {
		return repository.findByRestauranteIdAndId(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
				
	}
	
	@Transactional
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}

}
