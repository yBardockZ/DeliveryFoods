package br.com.ybardockz.api.v1.model.input;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import br.com.ybardockz.core.validation.FileContentType;
import br.com.ybardockz.core.validation.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	@Schema(description = "Arquivo da foto")
	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
	private MultipartFile arquivo;
	
	@Schema(example = "Foto picanha")
	@NotBlank
	private String descricao;

}
