package br.com.ybardockz.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.FotoProdutoModelAssembler;
import br.com.ybardockz.api.model.domain.FotoProdutoModel;
import br.com.ybardockz.api.model.input.FotoProdutoInput;
import br.com.ybardockz.domain.exception.EntidadeNaoEncontradaException;
import br.com.ybardockz.domain.model.FotoProduto;
import br.com.ybardockz.domain.model.Produto;
import br.com.ybardockz.domain.service.CadastroProdutoService;
import br.com.ybardockz.domain.service.CatalogoFotoProdutoService;
import br.com.ybardockz.domain.service.FotoStorageService;
import br.com.ybardockz.domain.service.FotoStorageService.FotoRecuperada;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produto/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoFotoProdutoService fotoProdutoService;
	
	@Autowired
	private CadastroProdutoService produtoService;	
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel uploadFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid @ModelAttribute FotoProdutoInput fotoInput) throws IOException {
		Produto produto = produtoService.buscarProdutoDoRestaurante(restauranteId, produtoId); 
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setContentType(fotoInput.getArquivo().getContentType());
		foto.setDescricao(fotoInput.getDescricao());
		foto.setTamanho(fotoInput.getArquivo().getSize());
		foto.setNomeArquivo(fotoInput.getArquivo().getOriginalFilename());
		
		FotoProduto fotoSalva = fotoProdutoService.salvar(foto, fotoInput.getArquivo().getInputStream());
		FotoProdutoModel fotoModel = fotoProdutoModelAssembler.toModel(fotoSalva);
		
		return fotoModel;
			
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {
		FotoProduto fotoProduto = fotoProdutoService.recuperarFotoDoProduto
				(restauranteId, produtoId);
		
		return fotoProdutoModelAssembler.toModel(fotoProduto);
		
	}
	
	@GetMapping
	public ResponseEntity<?> sevirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader("accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = fotoProdutoService.recuperarFotoDoProduto
					(restauranteId, produtoId);
				
			MediaType fotoMediaType = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediasType = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeArquivo(fotoMediaType, mediasType);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				
				return ResponseEntity
						.ok()
						.contentType(fotoMediaType)
						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""
						+ fotoProduto.getNomeArquivo() + "\"")
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
				
			} 
			
		} catch (EntidadeNaoEncontradaException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {
		fotoProdutoService.excluir(restauranteId, produtoId);
		
	}

	private void verificarCompatibilidadeArquivo(MediaType fotoMediaType, List<MediaType> mediasType) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediasType.stream()
				.anyMatch((mediaTypeHeader) -> mediaTypeHeader.isCompatibleWith(fotoMediaType));
		
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediasType);
		}
		
	}
	
}
