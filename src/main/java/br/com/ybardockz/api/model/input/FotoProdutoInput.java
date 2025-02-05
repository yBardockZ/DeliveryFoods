package br.com.ybardockz.api.model.input;

import org.springframework.web.multipart.MultipartFile;

import br.com.ybardockz.core.validation.FileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	@NotNull
	@FileSize(max = "500KB")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;

}
