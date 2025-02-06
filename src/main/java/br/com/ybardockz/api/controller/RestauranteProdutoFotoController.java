package br.com.ybardockz.api.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<InputStreamResource> sevirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {
		try {
			FotoProduto fotoProduto = fotoProdutoService.recuperarFotoDoProduto
					(restauranteId, produtoId);
			
			InputStream arquivo = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf(fotoProduto.getContentType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""
					+ fotoProduto.getNomeArquivo() + "\"")
					.body(new InputStreamResource(arquivo));
		} catch (EntidadeNaoEncontradaException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
}
