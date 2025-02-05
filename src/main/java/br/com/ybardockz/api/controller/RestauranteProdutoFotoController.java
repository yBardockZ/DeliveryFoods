package br.com.ybardockz.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.model.input.FotoProdutoInput;
import br.com.ybardockz.domain.exception.NegocioException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produto/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid @ModelAttribute FotoProdutoInput arquivo) {
		
		if (arquivo.getArquivo() == null) {
			throw new NegocioException("O arquivo não pode ser nulo.");
		}
		
		String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getArquivo().getOriginalFilename();
		var arquivoFoto = Path.of("/Users/Thalles/Desktop/catalogo", nomeArquivo);
		
		System.out.println(arquivo.getDescricao());
		System.out.println(arquivoFoto);
		System.out.println(arquivo.getArquivo().getContentType());
		
		try {
			arquivo.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
	}
	
}
