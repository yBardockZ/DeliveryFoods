package br.com.ybardockz.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.model.FotoProduto;
import br.com.ybardockz.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		Long restauranteId = foto.getProduto().getRestaurante().getId();
		Long produtoId = foto.getProduto().getId();
		
		Optional<FotoProduto> fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId);
		
		System.out.println(fotoProduto.isPresent());
		
		if (fotoProduto.isPresent()) {
			produtoRepository.deleteFoto(fotoProduto.get());
		}
		
		return produtoRepository.salvar(foto);
	}
	
}
