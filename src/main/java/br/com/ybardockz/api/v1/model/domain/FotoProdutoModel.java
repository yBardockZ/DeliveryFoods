package br.com.ybardockz.api.v1.model.domain;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {
	
	@Schema(example = "fotoPicanha.png")
	private String nomeArquivo;
	
	@Schema(example = "Foto de picanha suculenta")
	private String descricao;
	
	@Schema(example = "image/jpeg")
	private String contentType;
	
	@Schema(example = "92022")
	private Long tamanho;

}
