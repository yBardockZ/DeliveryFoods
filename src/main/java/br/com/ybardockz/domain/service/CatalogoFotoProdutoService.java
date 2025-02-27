package br.com.ybardockz.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.FotoProdutoNaoEncontradaException;
import br.com.ybardockz.domain.model.FotoProduto;
import br.com.ybardockz.domain.repository.ProdutoRepository;
import br.com.ybardockz.domain.service.FotoStorageService.NovaFoto;
import jakarta.transaction.Transactional;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getProduto().getRestaurante().getId();
		Long produtoId = foto.getProduto().getId();
		String novoNomeArquivo = fotoStorageService.gerarNome(foto.getNomeArquivo());
		
		Optional<FotoProduto> fotoProdutoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		String nomeArquivoExistente = null;
		
		if (fotoProdutoExistente.isPresent()) {
			nomeArquivoExistente = fotoProdutoExistente.get().getNomeArquivo();
			produtoRepository.deleteFoto(fotoProdutoExistente.get());
		}
		
		foto.setNomeArquivo(novoNomeArquivo);
		foto = produtoRepository.salvar(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.inputStream(dadosArquivo)
				.contentType(foto.getContentType())
				.nomeArquivo(foto.getNomeArquivo())
				.build();
		
		fotoStorageService.substituir(novaFoto, nomeArquivoExistente);
		
		return foto;
	}
	
	public FotoProduto recuperarFotoDoProduto(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = produtoRepository.findFotoById
				(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradaException(
						"A foto do produto de código: " + produtoId + " Não pode ser encontrada"));
		
		return fotoProduto;
	}
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = recuperarFotoDoProduto(restauranteId, produtoId);
		
		String nomeFotoProduto = fotoProduto.getNomeArquivo();
		produtoRepository.deleteFoto(fotoProduto);
		
		fotoStorageService.remover(nomeFotoProduto);
		
	}
	
}
