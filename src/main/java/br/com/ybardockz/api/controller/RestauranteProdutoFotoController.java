package br.com.ybardockz.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.assembler.FotoProdutoModelAssembler;
import br.com.ybardockz.api.model.domain.FotoProdutoModel;
import br.com.ybardockz.api.model.input.FotoProdutoInput;
import br.com.ybardockz.domain.model.FotoProduto;
import br.com.ybardockz.domain.model.Produto;
import br.com.ybardockz.domain.service.CadastroProdutoService;
import br.com.ybardockz.domain.service.CatalogoFotoProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produto/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoFotoProdutoService fotoProdutoService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
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
	
}
